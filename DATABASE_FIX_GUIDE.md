# Database Fix Implementation Guide

## Problem Summary
The error **"Query did not return a unique result: 2 results were returned"** occurs when:
1. There are duplicate trainer-subject records in the database (same empId + subjectId)
2. The `findByEmpIdAndSubjectId()` query returns Optional<TrainerSubject>, which expects 0 or 1 result
3. When 2+ duplicate records exist, the query fails

## Solution Implemented

### 1. **Database Level (Entity Class)**
Added UNIQUE constraint to prevent future duplicates:
```java
@Table(name = "trainer_subject", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"emp_id", "subject_id"}))
```

**File:** `trainerapp/src/main/java/com/example/trainerapp/entity/TrainerSubject.java`

### 2. **Query Optimization (Repository)**
Updated queries to handle duplicates gracefully:

#### Method 1: Use native SQL with LIMIT 1
```java
@Query(value = "SELECT * FROM trainer_subject WHERE emp_id = ?1 AND subject_id = ?2 LIMIT 1", 
       nativeQuery = true)
Optional<TrainerSubject> findByEmpIdAndSubjectId(Long empId, Long subjectId);
```

#### Method 2: Direct delete with @Modifying
```java
@Modifying
@Transactional
@Query(value = "DELETE FROM trainer_subject WHERE emp_id = ?1 AND subject_id = ?2 LIMIT 1", 
       nativeQuery = true)
int deleteByEmpIdAndSubjectId(Long empId, Long subjectId);
```

#### Method 3: Check existence
```java
boolean existsByEmpIdAndSubjectId(Long empId, Long subjectId);
```

#### Method 4: Cleanup duplicates
```java
@Modifying
@Transactional
@Query(value = "DELETE FROM trainer_subject WHERE id NOT IN (" +
       "SELECT MIN(id) FROM trainer_subject GROUP BY emp_id, subject_id LIMIT 1)", 
       nativeQuery = true)
int deleteDuplicates();
```

**File:** `trainerapp/src/main/java/com/example/trainerapp/repository/TrainerSubjectRepository.java`

### 3. **Service Layer Updates**
Updated to use new repository methods:
- `deleteAssignment(Long empId, Long subjectId)` - Uses optimized delete query
- `cleanupDuplicates()` - Calls repository cleanup method
- `existsByEmpIdAndSubjectId()` - Checks existence before operation

**File:** `trainerapp/src/main/java/com/example/trainerapp/service/TrainerSubjectService.java`

## Implementation Steps

### Step 1: Clean Existing Duplicates (If Any)

You have two options:

#### Option A: Using MySQL Command Line
```bash
mysql -u root -p trainerapp < CLEANUP_DUPLICATES.sql
```

#### Option B: Using REST API Endpoint
After deploying the new code, call:
```
POST http://localhost:8080/trainer-subject/cleanup-duplicates
```

#### Option C: Manual SQL (If you have access to MySQL)
```sql
-- Check for duplicates
SELECT emp_id, subject_id, COUNT(*) as count
FROM trainer_subject
GROUP BY emp_id, subject_id
HAVING COUNT(*) > 1;

-- Delete duplicates (keeps first record by ID)
DELETE FROM trainer_subject
WHERE id NOT IN (
  SELECT MIN(id)
  FROM (
    SELECT MIN(id) as id
    FROM trainer_subject
    GROUP BY emp_id, subject_id
  ) as keep_rows
);
```

### Step 2: Rebuild Backend
```bash
cd e:\nexanova\task1\trainerapp
mvn clean install -DskipTests=true
```

### Step 3: Restart Spring Boot Application
- Stop the current instance
- Start with: `mvn spring-boot:run`

### Step 4: Test Delete Functionality
1. Open browser: http://localhost:3000/assign-trainer-subject
2. Create an assignment
3. Click Delete button
4. Verify success message

## Verification Checklist

- [ ] Duplicates cleaned from database
- [ ] Backend rebuilt successfully
- [ ] Spring Boot application started without errors
- [ ] Frontend loads correctly
- [ ] Can create new assignments
- [ ] Can delete assignments without error
- [ ] Error messages show "Assignment deleted successfully"

## Troubleshooting

### If you still get "2 results" error:
1. Duplicates weren't cleaned - run cleanup SQL again
2. MySQL server not restarted - restart it
3. Application cache not cleared - do mvn clean again

### If delete button doesn't work:
1. Check browser console for errors
2. Check Spring Boot logs for exceptions
3. Verify empId and subjectId are being sent correctly

### To manually verify database state:
```sql
-- Connect to MySQL
mysql -u root -p

-- Use the database
USE trainerapp;

-- Check all assignments
SELECT * FROM trainer_subject;

-- Check for duplicates
SELECT emp_id, subject_id, COUNT(*) as count
FROM trainer_subject
GROUP BY emp_id, subject_id
HAVING COUNT(*) > 1;
```

## Files Modified

1. **Entity (Constraint Added)**
   - `TrainerSubject.java` - Added @UniqueConstraint

2. **Repository (Query Optimization)**
   - `TrainerSubjectRepository.java` - Added LIMIT 1 and new methods

3. **Service (Logic Updated)**
   - `TrainerSubjectService.java` - Updated to use new methods

4. **No Frontend Changes Needed**
   - `AssignTrainerSubject.js` - Already handles responses correctly

## Prevention for Future

The UNIQUE constraint at the database level prevents new duplicates from being created. If someone tries to insert a duplicate:
- Database will throw a constraint violation
- Application will catch and return error message
- Frontend will display user-friendly error message

## Next Steps After Verification

1. Test all CRUD operations
2. Add more trainers and subjects
3. Verify assignments work correctly
4. Test delete for multiple assignments
5. Document test results
