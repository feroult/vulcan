# Vulcan

## Exposing Eclipse JDT OSGi Bundle Programmatically

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/java-17+-blue.svg)
![Maven](https://img.shields.io/badge/maven-3.6.3+-blue.svg)

Vulcan is an experimental tool designed to expose the Eclipse JDT (Java Development Tools) OSGi bundle in a programmatic way, accessible via REST API, without the need to run the Eclipse workbench.

**Note:** This is an experimental project and is not actively maintained.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
  - [Running the Application](#running-the-application)
  - [Client Usage](#client-usage)
  - [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Extract Method**: Extracts a block of code into a new method.
- **Rename Type**: Renames a class or interface.
- **Rename Method**: Renames a method.
- **Rename Field**: Renames a field.
- **Rename Local Variable**: Renames a local variable.
- **Chained Refactorings**: Allows multiple refactorings to be applied in sequence.

## Installation

### Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher

### Build

Clone the repository and build the project using Maven:

```bash
git clone https://github.com/yourusername/vulcan.git
cd vulcan
./build.sh
```

## Usage

### Running the Application

You can run the application using the provided `run.sh` script. This script allows you to specify a workspace and whether to run in debug mode.

```bash
./run.sh [-w|--workspace <workspace_path>] [-d|--debug <y/n>]
```

- `-w|--workspace`: Path to the workspace (default: `../sample-workspace`)
- `-d|--debug`: Enable debug mode (`y` for yes, `n` for no, default: `n`)

Example:

```bash
./run.sh -w /path/to/workspace -d y
```

### Client Usage

You can use the `client.sh` script to send refactoring requests to the running application.

```bash
./client.sh
```

This script sends a POST request with a JSON payload specifying the refactorings to be applied.

Example payload:

```json
[
  {
    "type": "refactorings/core/extract-method",
    "methodName": "extractedPrintProduct",
    "offset": 123,
    "length": 61 
  },
  {
    "type": "refactorings/core/rename-type",
    "newName": "ProductX"  
  }
]
```

### Testing

Run the tests using the `test.sh` script:

```bash
./test.sh [-w|--workspace <workspace_path>]
```

- `-w|--workspace`: Path to the workspace (default: `/tmp/vulcan-test-workspace`)

Example:

```bash
./test.sh -w /path/to/test-workspace
```

## Contributing

We welcome contributions to the Vulcan project! If you have an idea for a new feature or have found a bug, please open an issue or submit a pull request.

### Steps to Contribute

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Create a new Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.