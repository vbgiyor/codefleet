Goal:

You made many changes on an old branch (let's call it old-branch).



## Goal
- You made many changes on an old branch (let's call it old-branch).
- You want those changes to end up on a new branch (say new-feature) that starts from the latest main.
- You haven't committed anything yet.
- You want to avoid committing on old-branch.

**Solution** 

Stash your changes temporarily:

`git stash push -m "temp-wip"`

Switch to the latest main:

`git checkout main`
`git pull origin main`  # Make sure it's the latest

Create a new branch from main:
`git checkout -b new-feature`

Apply your stashed changes:
`git stash pop`

✅ Now your changes are in new-feature, based off the latest main.
Continue working or commit and push:

`git add .`

`git commit -m "Describe your changes"`

`git push -u origin new-feature`

