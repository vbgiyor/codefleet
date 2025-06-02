# Contributing to Codefleet

Thank you for contributing to Codefleet, a project featuring a Django REST Framework backend, ReactJS frontend, and Selenium-based CFInspector automation module. This guide outlines our Git workflow and contribution process to ensure a clean and efficient development process.

## Git Workflow

We use a Gitflow-inspired branching model with `main` (production-ready code), `develop` (integration), and short-lived feature branches.

### Steps for Contributing

1. **Sync with `main`**:
   - Switch to `main` and pull the latest changes:
     ```bash
     git checkout main
     git pull origin main
     ```

2. **Sync `develop`**:
   - Switch to `develop`, pull, and merge `main` to keep it up to date:
     ```bash
     git checkout develop
     git pull origin develop
     git merge main
     git push origin develop
     ```

3. **Create a Feature Branch**:
   - Create a branch from `develop` with a descriptive name (e.g., `feature/task-0306`, `bug/fix-auth`):
     ```bash
     git checkout -b feature/task-0306 develop
     ```

4. **Make Changes**:
   - Work on your feature or bug fix.
   - Follow coding standards:
     - Backend: Adhere to Django/DRF conventions, use Ruff for linting.
     - Frontend: Use React best practices, Tailwind CSS for styling.
     - Automation: Update Selenium tests in the CFInspector module as needed.
   - Commit changes with clear messages:
     ```bash
     git add .
     git commit -m "Implement task-0306: Add new API endpoint"
     ```

5. **Push and Create PR**:
   - Push your branch:
     ```bash
     git push origin feature/task-0306
     ```
   - Create a pull request (PR) from your branch to `develop` on GitHub.
   - Include a description of changes and reference any related issues (e.g., `#33`).

6. **Code Review**:
   - Address feedback in the PR.
   - Update your branch if needed:
     ```bash
     git add .
     git commit -m "Address review comments"
     git push origin feature/task-0306
     ```

7. **Merge PR**:
   - Once approved, merge the PR into `develop` via GitHub.
   - A GitHub Action will automatically sync `develop` with `main` after merging `develop` to `main`.

8. **Merge `develop` to `main`**:
   - Create a PR from `develop` to `main` for release-ready changes.
   - Merge after review.
   - A GitHub Action will sync `develop` with `main` automatically.

9. **Delete Feature Branch**:
   - Delete the branch after merging:
     ```bash
     git push origin --delete feature/task-0306
     git branch -d feature/task-0306
     ```

### Using GitHub Desktop
- **Sync**: Switch to `main` or `develop`, click `Fetch/Pull origin`.
- **Create Branch**: Select `develop`, click `Branch > New Branch`, name it (e.g., `feature/task-0306`).
- **Commit**: Commit changes with a clear message, click `Publish branch`.
- **PR**: Create PRs on GitHub's website.
- **Merge**: Use `Branch > Merge into current branch` to sync `main` into `develop`.
- **Delete Branch**: Confirm deletion in GitHub Desktop after merging PRs.

### Best Practices
- **Keep `develop` Synced**: Always merge `main` into `develop` before creating new branches.
- **Clear Commit Messages**: Use descriptive messages (e.g., "Fix CFInspector back link issue").
- **Delete Merged Branches**: Clean up branches to avoid clutter.
- **Test Changes**: Run backend tests (`pytest`), frontend tests (`npm test`), and Selenium tests (`mvn test`) before pushing.
- **CI/CD**: Ensure changes pass GitHub Actions (e.g., `website_and_cfinspectorci.yml`).

### Local Testing
- **Backend**: Run `python manage.py test` for Django tests.
- **Frontend**: Run `npm test` for React tests.
- **Selenium**: Run `mvn test` for CFInspector tests.
- **Workflow Testing**: Use `act` to test GitHub Actions locally:
  ```bash
  act -W .github/workflows/sync-develop-with-main.yml
  ```

### Questions?
Reach out via GitHub issues or contact the maintainers (e.g., @vbgiyor, @Shekhar).