# Django Migrations Tutorial: Step-by-Step Guide with UserProfile Model & Database Population 🚀

**Date**: May 27, 2025  
**Purpose**: Learn Django migrations by creating, managing, and resolving conflicts using a `UserProfile` model, with initial database population and schema verification after each step.  
**Target Audience**: Beginners to intermediate Django developers.

---

## Introduction to Django Migrations 📋
Django migrations manage database schema changes over time, keeping the database in sync with your models. This tutorial uses a `UserProfile` model to demonstrate migrations, including conflict resolution, data migrations, custom operations, and database population. We'll populate the database initially with a JSON fixture and verify schema changes after each migration using SQLite commands.

---

## Prerequisites 🛠️
- Django project with a `profiles` app.
- SQLite database (default for simplicity; steps adaptable for PostgreSQL/MySQL).
- Basic knowledge of Django models and commands.

### Initial Setup
1. Create a Django project:
   ```bash
   django-admin startproject myproject
   cd myproject
   python manage.py startapp profiles
   ```
2. Add `profiles` to `INSTALLED_APPS` in `myproject/settings.py`:
   ```python
   INSTALLED_APPS = [
       ...
       'profiles',
   ]
   ```
3. Create a `UserProfile` model in `profiles/models.py`:
   ```python
   from django.db import models

   class UserProfile(models.Model):
       user_name = models.CharField(max_length=100)
   ```

---

## Step 1: Populate Initial Database 🌱
**Goal**: Create and load a JSON fixture to populate the `UserProfile` table before migrations.

1. **Create Initial Migration**:
   ```bash
   python manage.py makemigrations
   python manage.py migrate
   ```
   **Output**: Creates `profiles/migrations/0001_initial.py` and applies it to create the `userprofile` table.

2. **Create JSON Fixture**:
   Create `profiles/fixtures/initial_data.json`:
   ```json
   [
       {
           "model": "profiles.UserProfile",
           "pk": 1,
           "fields": {
               "user_name": "Shekhar B"
           }
       },
       {
           "model": "profiles.UserProfile",
           "pk": 2,
           "fields": {
               "user_name": "Chandra S"
           }
       }
   ]
   ```

3. **Load Fixture**:
   ```bash
   python manage.py loaddata initial_data
   ```
   **Result**: Populates `userprofile` table with two records.

4. **Verify Database Schema**:
   Open SQLite CLI:
   ```bash
   sqlite3 db.sqlite3
   ```
   Run:
   ```sql
   .schema userprofile
   ```
   **Expected Output**:
   ```sql
   CREATE TABLE userprofile (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       user_name VARCHAR(100) NOT NULL
   );
   ```
   Check data:
   ```sql
   SELECT * FROM userprofile;
   ```
   **Expected Output**:
   ```
   1|Alice Smith
   2|Bob Johnson
   ```

---

## Step 1.1: Create First Migration for a New Field ➕
**Goal**: Add an `email` field to `UserProfile`.

1. Update `profiles/models.py`:
   ```python
   class UserProfile(models.Model):
       user_name = models.CharField(max_length=100)
       email = models.EmailField(max_length=254)
   ```

2. Generate the migration:
   ```bash
   python manage.py makemigrations
   ```
   **Output**: Creates `profiles/migrations/0002_userprofile_email.py`:
   ```python
   from django.db import migrations, models

   class Migration(migrations.Migration):
       dependencies = [('profiles', '0001_initial')]
       operations = [
           migrations.AddField(
               model_name='UserProfile',
               name='email',
               field=models.EmailField(max_length=254),
           ),
       ]
   ```

3. Apply the migration:
   ```bash
   python manage.py migrate
   ```

4. **Verify Database Schema**:
   ```bash
   sqlite3 db.sqlite3
   .schema userprofile
   ```
   **Expected Output**:
   ```sql
   CREATE TABLE userprofile (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       user_name VARCHAR(100) NOT NULL,
       email VARCHAR(254) NOT NULL
   );
   ```
   Check data:
   ```sql
   SELECT * FROM userprofile;
   ```
   **Expected Output**:
   ```
   1|Alice Smith|
   2|Bob Johnson|
   ```

---

## Step 2: Create Second Migration for a New Field ➕
**Goal**: Add a `phone` field to `UserProfile`.

1. Update `profiles/models.py`:
   ```python
   class UserProfile(models.Model):
       user_name = models.CharField(max_length=100)
       email = models.EmailField(max_length=254)
       phone = models.CharField(max_length=15)
   ```

2. Generate the migration:
   ```bash
   python manage.py makemigrations
   ```
   **Output**: Creates `profiles/migrations/0003_userprofile_phone.py`:
   ```python
   from django.db import migrations, models

   class Migration(migrations.Migration):
       dependencies = [('profiles', '0002_userprofile_email')]
       operations = [
           migrations.AddField(
               model_name='UserProfile',
               name='phone',
               field=models.CharField(max_length=15),
           ),
       ]
   ```

3. Apply the migration:
   ```bash
   python manage.py migrate
   ```

4. **Verify Database Schema**:
   ```bash
   sqlite3 db.sqlite3
   .schema userprofile
   ```
   **Expected Output**:
   ```sql
   CREATE TABLE userprofile (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       user_name VARCHAR(100) NOT NULL,
       email VARCHAR(254) NOT NULL,
       phone VARCHAR(15) NOT NULL
   );
   ```

---

## Step 3: Create third migration Conflict with Non-Existent Column ⚠️
**Goal**: Create a migration referencing a non-existent column to cause a conflict.

1. Create an empty migration:
   ```bash
   python manage.py makemigrations --empty profiles
   ```
   **Output**: Creates `profiles/migrations/0004_auto_<timestamp>.py`.

2. Edit to reference a non-existent `bio` column:
   ```python
   from django.db import migrations

   class Migration(migrations.Migration):
       dependencies = [('profiles', '0003_userprofile_phone')]
       operations = [
           migrations.RunSQL("ALTER TABLE userprofile ADD bio TEXT;"),  # Non-existent column
       ]
   ```

3. Attempt to apply:
   ```bash
   python manage.py migrate
   ```
   **Error**: Fails because `bio` isn’t in the model, causing a schema mismatch.

4. **Verify Database Schema** (unchanged due to error):
   ```bash
   sqlite3 db.sqlite3
   .schema userprofile
   ```
   **Expected Output**: Same as Step 2 (no `bio` column).

---

## Step 4: Resolve Migration Conflict Manually 🛠️
**Goal**: Fix the conflict by removing the invalid operation.

1. Edit `profiles/migrations/0004_auto_<timestamp>.py` to remove `RunSQL`:
   ```python
   from django.db import migrations

   class Migration(migrations.Migration):
       dependencies = [('profiles', '0003_userprofile_phone')]
       operations = []
   ```

2. Apply the migration:
   ```bash
   python manage.py migrate
   ```
   **Result**: No changes (empty migration).

3. **Verify Database Schema**:
   ```bash
   sqlite3 db.sqlite3
   .schema userprofile
   ```
   **Expected Output**: Same as Step 2.

---

## Step 5: Create fifth Migration for New Field ✅
**Goal**: Add a `birth_date` field.

1. Update `profiles/models.py`:
   ```python
   class UserProfile(models.Model):
       user_name = models.CharField(max_length=100)
       email = models.EmailField(max_length=254)
       phone = models.CharField(max_length=15)
       birth_date = models.DateField()
   ```

2. Generate the migration:
   ```bash
   python manage.py makemigrations
   ```
   **Output**: Creates `profiles/migrations/0005_userprofile_birth_date.py`:
   ```python
   from django.db import migrations, models

   class Migration(migrations.Migration):
       dependencies = [('profiles', '0004_auto_<timestamp>')]
       operations = [
           migrations.AddField(
               model_name='UserProfile',
               name='birth_date',
               field=models.DateField(),
           ),
       ]
   ```

3. Apply the migration:
   ```bash
   python manage.py migrate
   ```

4. **Verify Database Schema**:
   ```bash
   sqlite3 db.sqlite3
   .schema userprofile
   ```
   **Expected Output**:
   ```sql
   CREATE TABLE userprofile (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       user_name VARCHAR(100) NOT NULL,
       email VARCHAR(254) NOT NULL,
       phone VARCHAR(15) NOT NULL,
       birth_date DATE NOT NULL
   );
   ```

---

## Step 6: Create sixth Data Migration using RunPython operation class 📝
**Goal**: Populate `birth_date` using `RunPython` with the `apps` argument.

1. Create an empty migration:
   ```bash
   python manage.py makemigrations --empty profiles
   ```
   **Output**: Creates `profiles/migrations/0006_auto_<timestamp>.py`.

2. Edit to populate `birth_date`:
   ```python
   from django.db import migrations
   from datetime import date

   def populate_birth_date(apps, schema_editor):
       UserProfile = apps.get_model('profiles', 'UserProfile')
       for profile in UserProfile.objects.all():
           profile.birth_date = date(1990, 1, 1)  # Default date
           profile.save()

   class Migration(migrations.Migration):
       dependencies = [('profiles', '0005_userprofile_birth_date')]
       operations = [
           migrations.RunPython(populate_birth_date),
       ]
   ```

3. Apply the migration:
   ```bash
   python manage.py migrate
   ```

4. **Verify Database Data**:
   ```bash
   sqlite3 db.sqlite3
   SELECT * FROM userprofile;
   ```
   **Expected Output**:
   ```
   1|Alice Smith||1990-01-01
   2|Bob Johnson||1990-01-01
   ```

---

## Step 7: Delete Model Field and Create seventh Migration 🗑️
**Goal**: Remove `email` and add `address`.

1. Update `profiles/models.py`:
   ```python
   class UserProfile(models.Model):
       user_name = models.CharField(max_length=100)
       phone = models.CharField(max_length=15)
       birth_date = models.DateField()
       address = models.CharField(max_length=200)
   ```

2. Generate the migration:
   ```bash
   python manage.py makemigrations
   ```
   **Output**: Creates `profiles/migrations/0007_remove_userprofile_email_userprofile_address.py`:
   ```python
   from django.db import migrations, models

   class Migration(migrations.Migration):
       dependencies = [('profiles', '0006_auto_<timestamp>')]
       operations = [
           migrations.RemoveField(
               model_name='UserProfile',
               name='email',
           ),
           migrations.AddField(
               model_name='UserProfile',
               name='address',
               field=models.CharField(max_length=200),
           ),
       ]
   ```

3. Apply the migration:
   ```bash
   python manage.py migrate
   ```

4. **Verify Database Schema**:
   ```bash
   sqlite3 db.sqlite3
   .schema userprofile
   ```
   **Expected Output**:
   ```sql
   CREATE TABLE userprofile (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       user_name VARCHAR(100) NOT NULL,
       phone VARCHAR(15) NOT NULL,
       birth_date DATE NOT NULL,
       address VARCHAR(200) NOT NULL
   );
   ```

---

## Step 8: Create eighth Migration with Conflict ⚠️
**Goal**: Reference deleted `email` field, then revert to fix.

1. Create an empty migration:
   ```bash
   python manage.py makemigrations --empty profiles
   ```
   **Output**: Creates `profiles/migrations/0008_auto_<timestamp>.py`.

2. Edit to reference `email`:
   ```python
   from django.db import migrations

   class Migration(migrations.Migration):
       dependencies = [('profiles', '0007_remove_userprofile_email_userprofile_address')]
       operations = [
           migrations.RunSQL("UPDATE userprofile SET email = 'default@example.com';"),
       ]
   ```

3. Attempt to apply:
   ```bash
   python manage.py migrate
   ```
   **Error**: Fails due to missing `email` column.

4. **Fix by Reverting**:
   - Revert to migration 0006:
     ```bash
     python manage.py migrate profiles 0006
     ```
   - Restore `email` in `profiles/models.py`:
     ```python
     class UserProfile(models.Model):
         user_name = models.CharField(max_length=100)
         email = models.EmailField(max_length=254)
         phone = models.CharField(max_length=15)
         birth_date = models.DateField()
         address = models.CharField(max_length=200)
     ```
   - Remove operation `RunSQL` added earlier in `profiles/migrations/0008_auto_<timestamp>.py`:
     ```python
     from django.db import migrations

     class Migration(migrations.Migration):
        dependencies = [('profiles', '0007_remove_userprofile_email_userprofile_address')]
        operations = [
     ]
     ```    
   - Generate a new migration:
     ```bash
     python manage.py makemigrations
     ```
     **Output**: Creates `profiles/migrations/0009_userprofile_email.py`:
     ```python
     from django.db import migrations, models

     class Migration(migrations.Migration):
         dependencies = [('profiles', '0006_auto_<timestamp>')]
         operations = [
             migrations.AddField(
                 model_name='UserProfile',
                 name='email',
                 field=models.EmailField(max_length=254),
             ),
         ]
     ```
   - Apply migrations:
     ```bash
     python manage.py migrate
     ```

5. **Verify Database Schema**:
   ```bash
   sqlite3 db.sqlite3
   .schema userprofile
   ```
   **Expected Output**:
   ```sql
   CREATE TABLE userprofile (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       user_name VARCHAR(100) NOT NULL,
       email VARCHAR(254) NOT NULL,
       phone VARCHAR(15) NOT NULL,
       birth_date DATE NOT NULL,
       address VARCHAR(200) NOT NULL
   );
   ```

---

## Step 9: Create Ninth Clean Migration ✅
**Goal**: Add a `bio` field.

1. Update `profiles/models.py`:
   ```python
   class UserProfile(models.Model):
       user_name = models.CharField(max_length=100)
       email = models.EmailField(max_length=254)
       phone = models.CharField(max_length=15)
       birth_date = models.DateField()
       address = models.CharField(max_length=200)
       bio = models.TextField()
   ```

2. Generate the migration:
   ```bash
   python manage.py makemigrations
   ```
   **Output**: Creates `profiles/migrations/0010_userprofile_bio.py`:
   ```python
   from django.db import migrations, models

   class Migration(migrations.Migration):
       dependencies = [('profiles', '0009_userprofile_email')]
       operations = [
           migrations.AddField(
               model_name='UserProfile',
               name='bio',
               field=models.TextField(),
           ),
       ]
   ```

3. Apply the migration:
   ```bash
   python manage.py migrate
   ```

4. **Verify Database Schema**:
   ```bash
   sqlite3 db.sqlite3
   .schema userprofile
   ```
   **Expected Output**:
   ```sql
   CREATE TABLE userprofile (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       user_name VARCHAR(100) NOT NULL,
       email VARCHAR(254) NOT NULL,
       phone视4. **Verify Database Data**:
   ```bash
   sqlite3 db.sqlite3
   SELECT * FROM userprofile;
   ```
   **Expected Output**:
   ```
   1|Alice Smith||1990-01-01||
   2|Bob Johnson||1990-01-01||
   ```

---

## Step 10: Tenth Migration with Operation Classes 🛠️
**Goal**: Use `CreateModel`, `AddField`, `AlterField`, and `RemoveField`.

1. Create an empty migration:
   ```bash
   python manage.py makemigrations --empty profiles
   ```

2. Edit `profiles/migrations/0011_auto_<timestamp>.py`:
   ```python
   from django.db import migrations, models

   class Migration(migrations.Migration):
       dependencies = [('profiles', '0010_userprofile_bio')]
       operations = [
           migrations.CreateModel(
               name='UserSettings',
               fields=[
                   ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                   ('theme', models.CharField(max_length=50)),
               ],
           ),
           migrations.AddField(
               model_name='UserProfile',
               name='nickname',
               field=models.CharField(max_length=50),
           ),
           migrations.AlterField(
               model_name='UserProfile',
               name='user_name',
               field=models.CharField(max_length=150),
           ),
           migrations.RemoveField(
               model_name='UserProfile',
               name='phone',
           ),
       ]
   ```

3. Apply the migration:
   ```bash
   python manage.py migrate
   ```

4. **Verify Database Schema**:
   ```bash
   sqlite3 db.sqlite3
   .schema userprofile
   .schema usersettings
   ```
   **Expected Output**:
   ```sql
   CREATE TABLE userprofile (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       user_name VARCHAR(150) NOT NULL,
       email VARCHAR(254) NOT NULL,
       birth_date DATE NOT NULL,
       address VARCHAR(200) NOT NULL,
       bio TEXT NOT NULL,
       nickname VARCHAR(50) NOT NULL
   );
   CREATE TABLE usersettings (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       theme VARCHAR(50) NOT NULL
   );
   ```

---

## Step 11: Create eleventh Custom Migration using forward and reverse methods of Operation class and Squash Migration 🔧
**Goal**: Create a custom operation and squash migrations.

1. Create `profiles/custom_operations.py`:
   ```python
   from django.db import migrations

   class AddCustomField(migrations.Operation):
       def __init__(self, model_name, field_name):
           self.model_name = model_name
           self.field_name = field_name

       def state_forwards(self, app_label, state):
           state.models[app_label, self.model_name.lower()].fields.append(
               (self.field_name, migrations.models.CharField(max_length=100))
           )

       def database_forwards(self, app_label, schema_editor, from_state, to_state):
           model = to_state.apps.get_model(app_label, self.model_name)
           schema_editor.add_field(
               model,
               model._meta.get_field(self.field_name)
           )

       def database_backwards(self, app_label, schema_editor, from_state, to_state):
           model = from_state.apps.get_model(app_label, self.model_name)
           schema_editor.remove_field(
               model,
               model._meta.get_field(self.field_name)
           )

       def describe(self):
           return f"Add custom field {self.field_name} to {self.model_name}"
   ```

2. Create a migration:
   ```bash
   python manage.py makemigrations --empty profiles
   ```
   Edit `profiles/migrations/0012_auto_<timestamp>.py`:
   ```python
   from django.db import migrations
   from profiles.custom_operations import AddCustomField

   class Migration(migrations.Migration):
       dependencies = [('profiles', '0011_auto_<timestamp>')]
       operations = [
           AddCustomField(
               model_name='UserProfile',
               field_name='status',
           ),
           migrations.CreateModel(
               name='UserLog',
               fields=[
                   ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                   ('action', models.CharField(max_length=100)),
               ],
           ),
       ]
   ```

3. Apply the migration:
   ```bash
   python manage.py migrate
   ```

4. **Verify Database Schema**:
   ```bash
   sqlite3 db.sqlite3
   .schema userprofile
   .schema userlog
   ```
   **Expected Output**:
   ```sql
   CREATE TABLE userprofile (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       user_name VARCHAR(150) NOT NULL,
       email VARCHAR(254) NOT NULL,
       birth_date DATE NOT NULL,
       address VARCHAR(200) NOT NULL,
       bio TEXT NOT NULL,
       nickname VARCHAR(50) NOT NULL,
       status VARCHAR(100) NOT NULL
   );
   CREATE TABLE userlog (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       action VARCHAR(100) NOT NULL
   );
   ```

5. **Squash Migrations** (0010–0012):
   ```bash
   python manage.py squashmigrations profiles 0010 0012
   ```
   Apply the squashed migration after deleting 0010–0012:
   ```bash
   python manage.py migrate
   ```

---

## Step 12: Demonstration Using SchemaEditor 🛠️
**Goal**: Use `schema_editor` to add an index.

1. Create an empty migration:
   ```bash
   python manage.py makemigrations --empty profiles
   ```

2. Edit `profiles/migrations/0013_auto_<timestamp>.py`:
   ```python
   from django.db import migrations

   def add_index(apps, schema_editor):
       schema_editor.execute(
           "CREATE INDEX userprofile_status_idx ON userprofile(status);"
       )

   def remove_index(apps, schema_editor):
       schema_editor.execute(
           "DROP INDEX userprofile_status_idx;"
       )

   class Migration(migrations.Migration):
       dependencies = [('profiles', '0012_auto_<timestamp>')]
       operations = [
           migrations.RunPython(add_index, remove_index),
       ]
   ```

3. Apply the migration:
   ```bash
   python manage.py migrate
   ```

4. **Verify Database Schema**:
   ```bash
   sqlite3 db.sqlite3
   .indices userprofile
   ```
   **Expected Output**:
   ```
   userprofile_status_idx
   ```

---

## Summary 🎉
This tutorial covered:
- Populating the database with a JSON fixture.
- Creating and applying migrations with schema verification.
- Resolving migration conflicts.
- Writing data migrations with `RunPython`.
- Using operation classes and custom operations.
- Squashing migrations and using `schema_editor`.

**Tips for Newcomers**:
- Always back up `db.sqlite3` before migrations.
- Use `python manage.py showmigrations` to track applied migrations.
- Verify schema changes with SQLite CLI after each migration.
- Test in a development environment.

Happy coding! 🚀