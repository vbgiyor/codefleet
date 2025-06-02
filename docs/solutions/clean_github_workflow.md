<!-- # Start fresh
git checkout main
git pull origin main
git checkout develop
git pull origin develop
git merge main
git push origin develop

# Create and work on feature branch
git checkout -b feature/task-0306 develop
# Make changes
git add .
git commit -m "Implement task-0306"
git push origin feature/task-0306

# Create PR from feature/task-0306 to develop, merge it
# Then merge develop to main
git checkout main
git pull origin main
git merge develop
git push origin main

# Re-sync develop
git checkout develop
git pull origin develop
git merge main
git push origin develop

# Delete feature branch
git push origin --delete feature/task-0306
git branch -d feature/task-0306 -->

# Clean Git Workflow for Codefleet

This document outlines a clean Git workflow for the `vbgiyor/codefleet` repository, using a Gitflow-inspired branching model with `main` (production-ready), `develop` (integration), and short-lived feature branches. It addresses issues like past commits (e.g., from May 25th) appearing in pull requests (PRs) due to unsynced branches and provides steps to maintain a tidy branch tree. The workflow is designed for the Codefleet project, which includes Django REST Framework, ReactJS, and Selenium-based CFInspector automation.

## Why Clean Workflow Matters

Without regular synchronization, the `develop` branch can accumulate commits not yet merged into `main`, causing PRs from `develop` to `main` to include outdated commits (e.g., `3855316`, `1781504`). This happens because feature branches merge into `develop` without updating `main` or re-syncing `develop`. The clean workflow ensures only relevant commits appear in PRs and keeps the branch history clear.

## Workflow Overview

1. Sync `main` with the remote repository.
2. Sync `develop` with `main` to prevent divergence.
3. Create feature branches from `develop` for new work.
4. Merge feature branches into `develop` via PRs.
5. Merge `develop` into `main` for releases.
6. Re-sync `develop` with `main` after merging.
7. Delete merged feature branches to avoid clutter.

## Step-by-Step Workflow

### 1. Sync `main`
Ensure `main` is up to date:
```bash
git checkout main
git pull origin main
```
- **GitHub Desktop**: Switch to `main`, click `Fetch/Pull origin`.

### 2. Sync `develop` with `main`
Keep `develop` aligned with `main`:
```bash
git checkout develop
git pull origin develop
git merge main
git push origin develop
```
- **GitHub Desktop**: Switch to `develop`, pull `origin/develop`, select `Branch > Merge into current branch`, choose `main`, push.
- **Why**: Prevents `develop` from accumulating commits (e.g., from May 25th) not in `main`.

### 3. Create a Feature Branch
Start new work from `develop`:
```bash
git checkout -b feature/task-0306 develop
```
- **GitHub Desktop**: Switch to `develop`, select `Branch > New Branch`, name it `feature/task-0306`.
- **Naming**: Use prefixes like `feature/`, `bug/`, or `task/` for clarity (e.g., `feature/cf-website-layout`).

### 4. Make Changes and Commit
```bash
git add .
git commit -m "Implement task-0306: Add new API endpoint"
git push origin feature/task-0306
```
- **GitHub Desktop**: Commit changes with a clear message, click `Publish branch`.
- **Tip**: Use descriptive commit messages (e.g., "Fix CFInspector back link").

### 5. Create PR to `develop`
- On GitHub, create a PR from `feature/task-0306` to `develop`.
- Verify only new commits appear in the PR (e.g., no May 25th commits like `1781504`).
- Merge after review and approval.

### 6. Merge `develop` into `main`
Propagate changes to `main`:
```bash
git checkout main
git pull origin main
git merge develop
git push origin main
```
- **GitHub Desktop**: Switch to `main`, pull, select `Branch > Merge into current branch`, choose `develop`, push.
- Create a PR from `develop` to `main` on GitHub for review, then merge.
- **Why**: Ensures `main` includes all `develop` commits, preventing bloated PRs.

### 7. Re-sync `develop` with `main`
Update `develop` to match `main`:
```bash
git checkout develop
git pull origin develop
git merge main
git push origin develop
```
- **GitHub Desktop**: Switch to `develop`, pull, merge `main` into `develop`, push.
- **Alternative (Reset)**:
  ```bash
  git checkout develop
  git fetch origin
  git reset --hard origin/main
  git push --force origin develop
  ```
  - **Warning**: `--force` rewrites history. Use only if no one else is working on `develop`.

### 8. Delete Merged Feature Branch
Clean up:
```bash
git push origin --delete feature/task-0306
git branch -d feature/task-0306
```
- **GitHub Desktop**: After merging the PR, confirm branch deletion when prompted.
- **Why**: Removes clutter (e.g., old branches like `feature/cf-website-layout-changes-3105`).

## Example Workflow

```bash
# Sync branches
git checkout main
git pull origin main
git checkout develop
git pull origin develop
git merge main
git push origin develop

# Create feature branch
git checkout -b feature/task-0306 develop
git add .
git commit -m "Implement task-0306"
git push origin feature/task-0306

# Create PR to develop, merge it
# Merge develop to main
git checkout main
git pull origin main
git merge develop
git push origin main

# Re-sync develop
git checkout develop
git pull origin develop
git merge main
git push origin develop

# Delete feature branch
git push origin --delete feature/task-0306
git branch -d feature/task-0306
```

## GitHub Desktop Equivalent

1. Switch to `main`, pull.
2. Switch to `develop`, pull, merge `main`, push.
3. Create branch `feature/task-0306` from `develop`.
4. Commit changes, publish branch.
5. Create PR on GitHub, merge to `develop`.
6. Switch to `main`, pull, merge `develop`, push.
7. Switch to `develop`, pull, merge `main`, push.
8. Delete `feature/task-0306`.

## Best Practices

- **Sync Regularly**: Merge `main` into `develop` after every `develop` to `main` merge to avoid commit accumulation (e.g., `3855316`, `3cb6b86`).
- **Descriptive Names**: Use `feature/`, `bug/`, or `task/` prefixes (e.g., `feature/task-0306`).
- **Delete Branches**: Remove merged branches to prevent clutter (e.g., `tasks/python-drf-2705`).
- **Avoid Force Pushes**: Use `git push --force` on `develop` only if no teammates are affected.
- **Optional Rebase**: For a linear history:
  ```bash
  git checkout feature/task-0306
  git rebase develop
  git push --force origin feature/task-0306
  ```
  - Coordinate with teammates to avoid conflicts.

## Fixing Past Commit Issues

If PRs include old commits (e.g., from May 25th):
1. Merge `develop` into `main`:
   ```bash
   git checkout main
   git pull origin main
   git merge develop
   git push origin main
   ```
2. Verify PR from `develop` to `main` is empty or contains only merge commits.
3. Reset `develop` to `main`:
   ```bash
   git checkout develop
   git fetch origin
   git reset --hard origin/main
   git push --force origin develop
   ```
4. Create new feature branches from updated `develop`.

## Clean Up Old Branches

Remove stale branches from `git log` (e.g., `feature/cf-website-layout-changes-3105`):
```bash
git fetch --prune
git branch -d <old-branch-name>
git push origin --delete <old-branch-name>
```

## Notes

- **Project Context**: Tailored for Codefleet’s Django, React, and CFInspector modules.
- **GitHub Desktop**: Supports most steps but use terminal for `git reset --hard`.
- **Time Zone**: Documented as of 07:00 PM IST, June 2, 2025.
- **Avoided Habits**:
  - Not syncing `develop` with `main` caused commit accumulation.
  - Manual PRs are fine but require synced branches.
  - Cluttered branches increase confusion.

This workflow ensures clean PRs and a tidy branch tree. Refer to `CONTRIBUTING.md` for team guidelines and `act-config.md` for local workflow testing.

---

## 🛠️ Automating Syncing with GitHub Actions

Configure local Github Workflow using `act`. Refer this [Instructions Guide](act-config.md) for more information on the same.

---

*Last Updated: June 02, 2025*
