# Ledger Application

## Overview
This is a simple console-based ledger application that allows users to manage transactions, including deposits and payments. The application reads and writes transactions from a CSV file and enables the user to view all transactions, deposits, or payments separately.

## Features
- **Main Menu**:
  - **D**: Add a deposit
  - **P**: Make a payment
  - **L**: View ledger (with filtering options)
  - **X**: Exit the program

- **Ledger**:
  - **A**: View all transactions
  - **D**: View only deposits
  - **P**: View only payments
  - **H**: Return to the main menu

## Transaction Format
Transactions are stored in the `transactions.csv` file with the following structure:

```
date|time|description|vendor|amount
```

Example:
```
2024-10-15|14:35:20|User Deposit|Vendor N/A|500.00
2024-10-15|16:45:10|Grocery Shopping|Walmart|-150.00
```

### Transaction Fields:
- **Date**: The date the transaction occurred, formatted as `yyyy-MM-dd`.
- **Time**: The time the transaction occurred, formatted as `HH:mm:ss`.
- **Description**: A brief description of the transaction.
- **Vendor**: The vendor involved in the transaction. If there is no vendor, it will default to `Vendor N/A`.
- **Amount**: The monetary amount of the transaction. Deposits are positive amounts, while payments are negative.

## Project Structure
```bash
src/
├── main/
│   ├── java/
│   │   └── com/plurasight/
│   │       └── App.java  # Main application logic
│   └── resources/
│       └── transactions.csv  # File that stores transactions data
```

## How to Run
1. Clone this repository.
2. Open the project in your preferred IDE.
3. Ensure that the `transactions.csv` file is located at `src/main/resources/`.
4. Run the `App.java` file.
5. Use the command-line interface to interact with the ledger, adding deposits or payments and viewing the ledger.

## Requirements
- **Java 11** or higher.
- A CSV file (`transactions.csv`) to store transaction data.

## Usage
Upon running the application, you will be presented with the main menu. You can enter a command to perform the following actions:

1. **Add Deposit**: Add a deposit amount to the ledger.
2. **Make Payment**: Record a payment (a negative transaction) in the ledger.
3. **Ledger**: View the transactions in the ledger, filtered by all entries, deposits, or payments.
4. **Exit**: Exit the application.

For deposits and payments, the user can input a description, vendor, and the amount. The transactions will be stored in the `transactions.csv` file and displayed in the ledger.

## CSV Format
The `transactions.csv` file must have the following headers (if not present, the program will still work but skip reading the first line):
```bash
date|time|description|vendor|amount
```

## Contributing
If you wish to contribute, feel free to submit pull requests or report issues.
