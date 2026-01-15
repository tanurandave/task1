# TODO: Add Delete Functionality for Subjects

## Backend Changes
- [x] Add `deleteSubject(Long id)` method in SubjectService.java to delete the subject and handle related records (TrainerSubject and SubjectTopic).
- [x] Add `@DeleteMapping("/{id}")` endpoint in SubjectController.java to call the delete service.

## Frontend Changes
- [x] Add `deleteSubject(id)` function in subjectService.js using `api.delete`.
- [x] Update SubjectList.js to add a delete button to each subject card and handle deletion by calling deleteSubject and updating the subjects state.

## Testing
- [x] Test the delete functionality to ensure subjects are removed from DB and UI updates.
- [x] Handle any potential errors or confirmations.
