# 🐍 Python Data Types and Type Conversions  
📅 **Last Updated:** May 28, 2025

This document covers  questions and practice exercises related to Python's basic data types (`int`, `float`, `str`, `bool`), the `type()` function, and type conversions. Each section includes explanations and code examples to help you understand and practice these concepts.

---

## 💼 Questions

Coding questions you might encounter in a Python related to `int`, `float`, `str`, `bool`, `type()`, and type conversions.

### 1. **What are the differences between `int` and `float` in Python? When would you use one over the other?**  
**Answer:**

- **`int`**: Represents whole numbers (e.g., `5`, `-10`) without a decimal point. Used for counting, indexing, or when decimal precision is not needed.
- **`float`**: Represents numbers with decimal points (e.g., `3.14`, `-0.001`). Used for calculations requiring precision, such as scientific computations or measurements.

**Use Case:**
- Use `int` for discrete values like loop counters or array indices.
- Use `float` for continuous values like measurements, percentages, or financial calculations.

**Example:**

```python
age = 25  # int for counting years
price = 19.99  # float for monetary value

print(type(age))   # <class 'int'>
print(type(price)) # <class 'float'>
```

### 2. **How does Python handle type conversion, and what is the difference between implicit and explicit type conversion?**

**Answer:**

* **Implicit Conversion**: Python automatically converts one data type to another during operations (e.g., `int` to `float` in arithmetic).
* **Explicit Conversion**: The programmer manually converts types using functions like `int()`, `float()`, `str()`, or `bool()`.

**Key Point**:
Implicit conversion avoids data loss (e.g., `int` to `float`), while explicit conversion may truncate or raise errors if invalid (e.g., `str` to `int` for non-numeric strings).

**Example:**

```python
# Implicit conversion
result = 5 + 2.0  # int + float -> float
print(result, type(result))  # 7.0 <class 'float'>

# Explicit conversion
num_str = "123"
num_int = int(num_str)  # str to int
print(num_int, type(num_int))  # 123 <class 'int'>
```

### 3. **What is the `type()` function in Python, and how is it useful?**

**Answer:**

The `type()` function returns the data type of a variable or expression (e.g., `<class 'int'>`, `<class 'str'>`).

**Usefulness**:

* **Debugging**: Identify variable types in complex code.
* **Conditional logic**: Check types before operations to avoid errors.
* **Dynamic behavior**: Adapt code based on data types.

**Example:**

```python
value = "Hello"
print(type(value))  # <class 'str'>

if type(value) == str:
    print("It's a string!")
```

### 4. **What happens when you try to convert a non-numeric string to an `int` or `float`?**

**Answer:**

Attempting to convert a non-numeric string (e.g., `"abc"`) to `int` or `float` raises a `ValueError`.
Only strings representing valid numbers (e.g., `"123"`, `"3.14"`) can be converted.

**Example:**

```python
valid_str = "123"
invalid_str = "abc"

print(int(valid_str))  # 123
# print(int(invalid_str))  # Raises ValueError: invalid literal for int()
```

### 5. **How does Python evaluate `bool` values for non-boolean types? Explain the concept of "truthiness."**

**Answer:**

Python evaluates non-boolean types as `True` or `False` based on their **"truthiness"**.

* **Falsey values**:

  * `0`, `0.0` (numbers)
  * `""` (empty string)
  * `[]`, `{}`, `()` (empty collections)
  * `None`
  * `False`
* **Truthy values**:
  Non-zero numbers, non-empty strings/collections, and most other objects.

**Used in conditionals (`if`, `while`) and boolean operations.**

**Example:**

```python
print(bool(0))       # False
print(bool(42))      # True
print(bool(""))      # False
print(bool("hello")) # True
print(bool([]))      # False
```

### 6. **What are some common pitfalls when working with type conversions in Python?**

**Answer:**

* **Loss of precision**: Converting `float` to `int` truncates decimal parts (e.g., `int(3.99)` yields `3`).
* **Invalid conversions**: Converting non-numeric strings to `int`/`float` raises `ValueError`.
* **Floating-point imprecision**: Operations with `float` may lead to unexpected results due to IEEE 754 representation (e.g., `0.1 + 0.2 != 0.3`).
* **Boolean conversion surprises**: Unexpected truthiness (e.g., `bool("False")` is `True` because it's a non-empty string).

**Example:**

```python
print(int(3.99))     # 3 (truncates decimal)
print(0.1 + 0.2)     # 0.30000000000000004
print(bool("False")) # True
```

---

## 🧪 Practice Questions

These are coding exercises to help you practice working with `int`, `float`, `str`, `bool`, `type()`, and type conversions. Each question includes a solution and explanation.

### 1. **Write a Python program that takes a user input as a string and converts it to an `int` or `float` based on whether it contains a decimal point.**

**Solution:**

```python
user_input = input("Enter a number: ")

if '.' in user_input:
    number = float(user_input)
    print(f"Float: {number}, Type: {type(number)}")
else:
    number = int(user_input)
    print(f"Integer: {number}, Type: {type(number)}")
```

**Explanation**:

* Checks for a decimal point to decide the type.
* Uses `float()` or `int()` for conversion.
* Handles invalid inputs poorly (raises `ValueError`); in a real application, add error handling with `try-except`.

---

### 2. **Write a function that checks the type of a variable and returns a string describing it (e.g., "Integer", "Float", etc.).**

**Solution:**

```python
def describe_type(value):
    if type(value) == int:
        return "Integer"
    elif type(value) == float:
        return "Float"
    elif type(value) == str:
        return "String"
    elif type(value) == bool:
        return "Boolean"
    else:
        return "Other"

# Test cases
print(describe_type(42))     # Integer
print(describe_type(3.14))   # Float
print(describe_type("hello"))# String
print(describe_type(True))   # Boolean
```

**Explanation**:

* Uses `type()` to check the variable's type.
* Returns a descriptive string based on the type.
* Can be extended to handle other types like `list`, `dict`, etc.

---

### 3. **Write a program that converts a list of mixed strings (e.g., `["123", "4.56", "abc"]`) into a list of numbers where possible, leaving invalid strings as is.**

**Solution:**

```python
def convert_strings_to_numbers(string_list):
    result = []
    for item in string_list:
        try:
            if '.' in item:
                result.append(float(item))
            else:
                result.append(int(item))
        except ValueError:
            result.append(item)
    return result

# Test
strings = ["123", "4.56", "abc", "789"]
print(convert_strings_to_numbers(strings))  # [123, 4.56, 'abc', 789]
```

**Explanation**:

* Iterates through the list, attempting to convert each string.
* Uses `try-except` to handle invalid conversions.
* Checks for decimal points to decide between `int` and `float`.

---

Ah, you're right! Here's the explanation for the last question:

---

### 4. **Write a function that takes a number (`int` or `float`) and returns its string representation with a prefix indicating its type.**

**Solution:**

```python
def number_to_string(num):
    if type(num) == int:
        return f"Int: {num}"
    elif type(num) == float:
        return f"Float: {num}"
    else:
        return "Invalid input"

# Test
print(number_to_string(42))    # "Int: 42"
print(number_to_string(3.14))  # "Float: 3.14"
```

**Explanation**:

* The function checks the type of the input (`num`) using `type()`.
* If the type is `int`, it returns a string with the prefix `"Int: "` followed by the number.
* If the type is `float`, it returns a string with the prefix `"Float: "` followed by the number.
* If the input is neither an `int` nor a `float`, it returns `"Invalid input"`. This helps handle edge cases where the input might not be a valid number.
