# TODO List for Implementing All Features

## Completed Features
- [x] 1. /trainer Post - Add a new trainer to the database (already implemented)
- [x] 2. /trainer Get - Get the list of all trainers in the database (already implemented)
- [x] 3. /trainer Delete - Remove a specific trainer from the database (already implemented)
- [x] 4. /trainer/{id} Get - Get the information of the trainer whose empId matches the id (already implemented)
- [x] 5. /trainer/{subject}/topic Get - Get the information of the trainer(s) who teach the specific subject (implemented)
- [x] 6. /subject Post - Add a new subject to the institute’s master file (already implemented)
- [x] 7. /subject Get - List of all subjects taught at the institute (already implemented)
- [x] 8. /subject/[id] Get - List of subjects with all the trainer’s teaching the topic as well (implemented)

## Implementation Details
- Created SubjectWithTrainers DTO for feature 8
- Added custom query in TrainerRepository for feature 5
- Updated services and controllers accordingly
- All features are now implemented as per the stricket

## Next Steps
- Test the application to ensure all endpoints work correctly
- Verify database relationships and data integrity
