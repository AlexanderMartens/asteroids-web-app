# **Styles**

## General Guidelines
- All names should only include letters `a-z`, numbers `0-9`, and underscore `_`.
- Maintain consistency across files.

## Code Formatting
### Indentation
Use `tab` when indenting. 

### Braces & Spacing
- Use **K&R style** for curly braces:
    ```cpp
    if (condition) {
        doSomething();
    } else {
        doSomethingElse();
    }
    ```
- Always put a space before opening parentheses in control structures:
    ```python
    if (x == 5):
        print("x is 5")
    ```
- Always put a space before and after operation signs:
    ```python
    x = 10
    while (x <= 20):
    x += 1
    ```

### Comments
- Use single-line comments (`//` or `#`) for short explanations. 
- Use multi-line comments (`/* ... */` or `''' ... '''`) for longer descriptions.
- Use multi-line comments (`/* ... */` or `''' ... '''`) before functions to describe them. Include parameters and their output:
    ```python
    '''
    @param x: int
    @return int
    '''
    def function(x):
        return x + 1
    ```

## Naming Conventions
- **Variables & Functions**: Use `snake_case` (e.g., `calculate_sum`).
- **Constants**: Use `UPPER_CASE` (e.g., `MAX_RETRIES`).
- **Classes**: Use `PascalCase` (e.g., `UserManager`).
- **Files**: Use `snake_case` (e.g., `data_processor.py`), unless another naming convention is needed or highly reccomended for a language (ex: `React`, `Markdown`, etc.).

## Git Commit Messages
- Use **past tense** (e.g., "Fixed bug in user authentication").
- Keep messages concise

## Language Specific Guidelines
*To be added onto those listed above if applicable.*
### CSS Guidelines
- Use **BEM naming convention** for class names:
    ```css
    .button {
        background: blue;
        color: white;
        padding: 10px 20px;
        border-radius: 5px;
    }

    .button--large {
        padding: 15px 30px;
    }

    .button__icon {
        margin-right: 5px;
    }
    ```

- Avoid `!important` unless absolutely necessary.
- Use variables for reusable values (if using SCSS):
    ```scss
    $primary-color: #3498db;
    ```

### JavaScript Guidelines
- Use **ES6+ syntax** (e.g., `const`, `let`, arrow functions).
- Prefer functional components over class components in React.
- Use `propTypes` or `TypeScript` for type safety in React.
- Use destructuring when handling function arguments:
  ```js
  function greet({ name, age }) {
      console.log(`Hello, ${name}. You are ${age} years old.`);
  }
  ```
- Use template literals instead of string concatenation:
  ```js
  const name = "John";
  console.log(`Hello, ${name}!`);
  ```
- Organize functions and modules logically, keeping related functions together.


### React Guidelines
- Use **JSX syntax** properly, wrapping multiple elements in a parent container:
  ```jsx
  function MyComponent() {
      return (
          <div>
              <h1>Hello, world!</h1>
          </div>
      );
  }
  ```
- Use **`useEffect`** and **`useState`** wisely to manage state and side effects.
- Use **context API or Redux** for global state management when necessary.
- Follow component composition best practices:
  ```jsx
  function Button({ text }) {
      return <button>{text}</button>;
  }
  
  function App() {
      return <Button text="Click Me" />;
  }
  ```
- Keep components **small and reusable**, breaking down large components into smaller ones.
- Use `TypeScript` for defining default values for props.

### Python Guidelines
- Follow **PEP 8** style guide.
- Use type hints where possible:
  ```python
  def add(x: int, y: int) -> int:
      return x + y
  ```
- Use list comprehensions instead of loops when appropriate.
