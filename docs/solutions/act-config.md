# Configuring and Using `act` for Local GitHub Actions on macOS (M-series)

This document outlines how to set up and use `act` to run GitHub Actions workflows locally on an Apple M-series Mac, specifically for the `sync-develop-with-main.yml` workflow in the `vbgiyor/codefleet` repository. It includes steps to configure SSH authentication and handle the `.secrets` file for the `SSH_PRIVATE_KEY`.

## Why GitHub Actions Is Better

- **Automation**: Runs automatically on push to `main`, no manual intervention.
- **Consistency**: Executes in a controlled environment, reducing local setup issues.
- **Team-Friendly**: No need for team members to run scripts locally.
- **Audit Trail**: Logs are stored in GitHub Actions for debugging.


## Prerequisites

- **macOS**: Apple M-series chip (e.g., M1, M2).
- **Docker**: Install Docker Desktop and ensure it’s running (`docker ps`).
- **Homebrew**: For installing `act` (`brew install act`).
- **Git**: Configured with SSH access to GitHub.
- **Repository**: Cloned `vbgiyor/codefleet` locally.
- **SSH Key**: `~/.ssh/id_ed25519` and `~/.ssh/id_ed25519.pub` set up.

## Installation

1. **Install Docker Desktop**:
   - Download from [Docker Hub](https://www.docker.com/products/docker-desktop/).
   - Start Docker: `docker ps` to verify.

2. **Install `act`**:
   ```bash
   brew install act
   ```
   - Verify: `act --version`.

3. **Update `act`** (if needed):
   ```bash
   brew upgrade act
   ```

## Configure SSH Authentication

1. **Verify SSH Key**:
   ```bash
   ls -l ~/.ssh/id_ed25519
   ```
   - If missing, generate:
     ```bash
     ssh-keygen -t ed25519 -C "your_email@example.com"
     ```
   - View private key (if needed):
     ```bash
     cat -v ~/.ssh/id_ed25519
     ```

2. **Add Public Key to GitHub**:
   ```bash
   cat ~/.ssh/id_ed25519.pub
   ```
   - Copy output, go to GitHub > Settings > SSH and GPG keys > New SSH key, paste, and save.

3. **Test SSH**:
   ```bash
   ssh -T git@github.com
   ```
   - Should return: `Hi vbgiyor!`.

## Set Up `.secrets` File

The `sync-develop-with-main.yml` workflow requires an `SSH_PRIVATE_KEY` for authentication. Encode the private key as a single-line base64 string.

1. **Encode SSH Private Key**:
   ```bash
   base64 -i ~/.ssh/id_ed25519 | tr -d '\n' > ssh_key_base64.txt
   ```
   - Verify (alternative to `cat -A`):
     ```bash
     cat -v ssh_key_base64.txt
     ```
     - Should be a single line of base64 text (e.g., `c3NoLWVkMjU1MTk...`).

2. **Create `.secrets`**:
   ```bash
   echo "SSH_PRIVATE_KEY=$(cat ssh_key_base64.txt)" > .secrets
   ```
   - Verify:
     ```bash
     cat -v .secrets
     ```
     - Should show: `SSH_PRIVATE_KEY=<base64-string>` (no newlines).

3. **Secure and Ignore**:
   ```bash
   chmod 600 .secrets
   echo ".secrets" >> .gitignore
   ```

4. **Add to GitHub Secrets**:
   - Go to GitHub > Repository (`vbgiyor/codefleet`) > Settings > Secrets and variables > Actions > New repository secret.
   - Name: `SSH_PRIVATE_KEY`.
   - Value: Paste contents of `ssh_key_base64.txt`.
   - Save.

## Workflow File

Ensure `.github/workflows/sync-develop-with-main.yml` is configured:
```yaml
name: Sync Develop with Main
on:
  push:
    branches:
      - main
jobs:
  sync:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
        with:
          fetch-depth: 0
      - name: Set up SSH
        run: |
          mkdir -p ~/.ssh
          echo "$SSH_PRIVATE_KEY" | base64 -d > ~/.ssh/id_ed25519
          chmod 600 ~/.ssh/id_ed25519
          ssh-keyscan github.com >> ~/.ssh/known_hosts
          git config --global core.sshCommand "ssh -i ~/.ssh/id_ed25519"
      - name: Merge main into develop
        run: |
          git config user.name "GitHub Actions Bot"
          git config user.email "actions@github.com"
          git fetch origin
          git checkout develop
          git merge origin/main --no-edit
          git push origin develop
    env:
      SSH_PRIVATE_KEY: ${{ secrets.SSH_PRIVATE_KEY }}
```

## Run `act`

1. **Navigate to Repository**:
   ```bash
   cd /Users/redstar/pyc/missionmq/codefleet
   ```

2. **Run Workflow**:
   ```bash
   act -W .github/workflows/sync-develop-with-main.yml --secret-file .secrets --container-architecture linux/amd64 --verbose
   ```
   - `--container-architecture linux/amd64`: Ensures compatibility on M-series Macs.
   - `--verbose`: Provides detailed output for debugging.

3. **Alternative: Environment Variable**:
   ```bash
   export SSH_PRIVATE_KEY=$(cat ssh_key_base64.txt)
   act -W .github/workflows/sync-develop-with-main.yml --container-architecture linux/amd64 --verbose
   ```

## Troubleshoot

- **Error: `unexpected character "\n"`**:
  - Check `.secrets`:
    ```bash
    cat -v .secrets
    ```
    - Re-create if newlines appear:
      ```bash
      rm .secrets
      base64 -i ~/.ssh/id_ed25519 | tr -d '\n' > ssh_key_base64.txt
      echo "SSH_PRIVATE_KEY=$(cat ssh_key_base64.txt)" > .secrets
      ```
- **Docker Issues**:
  - Ensure Docker is running: `docker ps`.
  - Start Docker Desktop if needed.
- **Authentication Failure**:
  - Verify SSH: `ssh -T git@github.com`.
  - Ensure `SSH_PRIVATE_KEY` matches GitHub’s public key.
- **Verbose Output**:
  ```bash
  act -W .github/workflows/sync-develop-with-main.yml --secret-file .secrets --container-architecture linux/amd64 --verbose
  ```

## Alternative: Use PAT

If SSH fails, use a Personal Access Token (PAT):
1. **Generate PAT**:
   - GitHub > Settings > Developer settings > Personal access tokens > Tokens (classic) > Generate new token.
   - Scopes: `repo`, `workflow`.
   - Copy token (e.g., `ghp_XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX`).
2. **Update `.secrets`**:
   ```bash
   echo "GITHUB_TOKEN=ghp_XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" > .secrets
   ```
3. **Update Workflow**:
   ```yaml
   name: Sync Develop with Main
   on:
     push:
       branches:
         - main
   jobs:
     sync:
       runs-on: ubuntu-latest
       steps:
         - uses: actions/checkout@v3
           with:
             fetch-depth: 0
             token: ${{ secrets.GITHUB_TOKEN }}
         - name: Merge main into develop
           run: |
             git config user.name "GitHub Actions Bot"
             git config user.email "actions@github.com"
             git fetch origin
             git checkout develop
             git merge origin/main --no-edit
             git push origin develop
   ```
4. **Run**:
   ```bash
   act -W .github/workflows/sync-develop-with-main.yml --secret-file .secrets --container-architecture linux/amd64 --verbose
   ```

## Fallback: Manual Sync

If you prefer a local shell script (as you initially considered), you can create one to replicate the GitHub Actions workflow. This is useful if you don't want to rely on `act` or need to run the sync locally without Docker.

### `sync-develop.sh`

```bash
#!/bin/bash

# Exit on error
set -e

# Ensure we're in the correct repository
echo "Synchronizing develop with main..."

# Switch to main and pull latest
git checkout main
git pull origin main

# Switch to develop and pull latest
git checkout develop
git pull origin develop

# Merge main into develop
git merge main

# Push updated develop
git push origin develop

echo "Sync completed successfully!"
````

---

### ✅ Steps to Use

1. **Save the script** as `sync-develop.sh` in your repository (e.g., in a `scripts/` directory).
2. **Make it executable**:

   ```bash
   chmod +x sync-develop.sh
   ```
3. **Run it**:

   ```bash
   ./sync-develop.sh
   ```
4. (Optional) **Add it to `.gitignore`** if you don’t want to track it:

   ```bash
   echo "sync-develop.sh" >> .gitignore
   ```

---

### 📌 When to Run

Execute this script **after merging a PR from `develop` to `main`** to ensure `develop` stays synced with `main`.

## Verify

- After running `act`, check `develop`:
  ```bash
  git fetch origin
  git log origin/develop --oneline --graph
  ```
- Test on GitHub by pushing to `main`.

## Notes

- **macOS `base64`**: Use `base64 -i` for input files.
- **Security**: Never commit `.secrets` or `ssh_key_base64.txt`.
- **M-series**: Always use `--container-architecture linux/amd64`.

---

### **Last Updated**: *Configured as of 06:50 PM IST, June 2, 2025.*

---