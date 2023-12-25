# IN2030 Project: Asp Interpreter

This repository showcases the Asp Interpreter, a major project completed as part of the IN2030 - Project in Programming course. The project centers around developing an interpreter for Asp, a mini-Python programming language. It demonstrates a blend of practical coding skills with a deep understanding of the theoretical aspects of programming languages.

## Objective

The primary objective of this project was to gain hands-on experience in crafting a significant software application. It emphasizes crucial software engineering principles such as structured programming, object-oriented design, and modularization. The interpreter effectively reads, analyzes, and executes Asp code, showcasing my ability to translate complex programming concepts into functional software.

## Project Structure

The Asp Interpreter is structured into four comprehensive parts, each addressing a key aspect of interpreter design:

- **Scanner**: A module designed to tokenize the Asp program text, efficiently converting source code into a stream of tokens.
- **Parser**: This component ensures that the sequence of tokens follows the syntactical rules of Asp, effectively validating the program's structure.
- **Expression Interpreter**: A critical part of the interpreter that evaluates expressions within the Asp code, including type validation and error handling.
- **Full Interpreter**: The culmination of the project, this module brings all components together to interpret entire Asp programs. It can handle various constructs like functions, loops, conditionals, and expressions, demonstrating a comprehensive understanding of language interpretation.

## How to Run

To run the Asp Interpreter:

```bash
java -jar asp.jar asp_program.asp
