# Contributing to the Web Application

This document outlines the best practices for managing Git branches and releases in this repository. We use the **Gitflow** branching strategy to ensure a structured and stable development process for our web application. For smaller teams or simpler workflows, we also reference the **GitHub Flow** as an alternative.

## Branching Strategy

### Gitflow (Primary Workflow)
- **`main` Branch**: Contains production-ready, stable code. Only merged from release or hotfix branches.
- **`develop` Branch**: Integration branch for features and bug fixes. Reflects the latest development state.
- **Feature Branches**: For new features or tasks (e.g., `feature/login-page`). Branched off `develop`, merged back via pull requests (PRs).
- **Bugfix Branches**: For fixing bugs in production (e.g., `bugfix/fix-login-error`). Branched off `main`, merged into `main` and `develop`.
- **Release Branches**: For preparing releases (e.g., `release/v1.0.0`). Branched off `develop`, used for final testing and minor fixes, then merged into `main` and `develop`.
- **Hotfix Branches**: For urgent production fixes (e.g., `hotfix/v1.0.1`). Branched off `main`, merged back to `main` and `develop`.

### GitHub Flow (Alternative for Simpler Projects)
- Use a single `main` branch for production-ready code.
- Create feature branches for all changes (e.g., `add-user-auth`).
- Merge changes into `main` via PRs after code review and testing.
- Deploy directly from `main` or tag releases from it.

### When to Use
- **Gitflow**: Best for complex projects, larger teams, or scheduled releases.
- **GitHub Flow**: Ideal for smaller teams, continuous deployment, or rapid iterations.

## Creating and Managing Branches
1. **Initialize Repository**:
   ```bash
   git init
   git checkout -b main
   git commit -m "Initial commit"
   git push origin main
   ```
2. **Create `develop` Branch (Gitflow)**:
   ```bash
   git checkout -b develop
   git push origin develop
   ```
3. **Create Feature/Bugfix Branches**:
   ```bash
   git checkout -b feature/login-page develop
   git push origin feature/login-page
   ```
4. **Merge via Pull Requests**:
   - Push changes to the feature branch.
   - Open a PR targeting `develop` (or `main` for GitHub Flow).
   - Ensure tests pass and get code review approval.
   - Merge and delete the branch:
     ```bash
     git branch -d feature/login-page
     git push origin --delete feature/login-page
     ```
5. **Protect Branches**:
   - In GitHub, go to Settings > Branches > Add branch protection rule for `main` and `develop`.
   - Enable:
     - Require PRs before merging.
     - Require status checks (e.g., CI tests).
     - Restrict direct pushes.
6. **Keep Branches Updated**:
   - Rebase or merge `develop` into feature branches to avoid conflicts:
     ```bash
     git checkout feature/login-page
     git rebase develop
     git push --force
     ```

## Managing Releases
Releases are snapshots of the code tagged with a version number, used for deploying the web application.

### Best Practices
- **Semantic Versioning (SemVer)**: Use `MAJOR.MINOR.PATCH` (e.g., `1.0.0`).
  - `MAJOR`: Breaking changes.
  - `MINOR`: New features.
  - `PATCH`: Bug fixes.
- **Tag Releases**:
  ```bash
  git checkout main
  git tag -a v1.0.0 -m "Release v1.0.0: Initial stable version"
  git push origin v1.0.0
  ```
  - In GitHub, go to Releases > Create a new release, select the tag, and add release notes.
- **Release Branches (Gitflow)**:
  ```bash
  git checkout -b release/v1.0.0 develop
  ```
  - Update version numbers (e.g., in `package.json`), make minor fixes, and merge:
    ```bash
    git checkout main
    git merge release/v1.0.0
    git tag -a v1.0.0
    git push origin main v1.0.0
    git checkout develop
    git merge release/v1.0.0
    git push origin develop
    git branch -d release/v1.0.0
    ```
- **Automate with CI/CD**:
  - Use GitHub Actions to automate testing and deployment.
  - Example workflow (`.github/workflows/deploy.yml`):
    ```yaml
    name: Deploy
    on:
      push:
        tags:
          - 'v*'
    jobs:
      deploy:
        runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up Node.js
        uses: actions/setup-node@v3
        with:
          node-version: '16'
      - run: npm install
      - run: npm test
      - name: Deploy to Production
        run: npm run deploy
        env:
          DEPLOY_KEY: ${{ secrets.DEPLOY_KEY }}
    ```
- **Maintain a Changelog**:
  - Keep a `CHANGELOG.md` file following the [Keep a Changelog](https://keepachangelog.com/) format:
    ```markdown
    # Changelog
    ## [1.0.0] - 2025-05-25
    ### Added
    - User authentication module
    - REST API for user management
    ### Fixed
    - UI alignment issues on mobile
    ```

### Basis for Releases
- **Feature Completion**: Release when a set of features is complete and tested.
- **Stability**: Ensure code passes all tests and is stable in staging.
- **User Needs**: Align with user or business requirements.
- **Hotfixes**: Create patch releases for critical bugs (e.g., `1.0.1`).

## Web Application-Specific Tips
- **Frontend and Backend**: If separate repositories, coordinate versioning (e.g., `frontend/v1.0.0` and `backend/v1.0.0`).
- **Environment Configs**: Use `.env` files or GitHub Secrets for environment-specific settings (e.g., API URLs).
- **Continuous Deployment**: Deploy `main` to staging automatically; tag releases for production.
- **Tools**: Use Vercel, Netlify, or AWS for deployment.

## General Maintenance
- **Clean Up**: Delete merged branches to keep the repository tidy.
- **Automate Testing**: Run tests on every PR using GitHub Actions.
- **Document**: Reference this file in your PRs and issues.
- **Link Issues**: Connect PRs to GitHub issues for traceability.
- **Monitor**: Check CI/CD pipelines for failures.

## Example Workflow (Gitflow)
1. Create `feature/add-user-profile` from `develop`.
2. Push changes, open a PR to `develop`, review, and merge.
3. Create `release/v1.0.0` from `develop` when ready.
4. Update `package.json`, fix minor issues, and merge into `main` and `develop`.
5. Tag `v1.0.0`, create a GitHub release, and deploy.
6. Update `CHANGELOG.md`.

For questions or clarifications, open an issue or contact the repository maintainers.