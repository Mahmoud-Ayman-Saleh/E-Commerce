# Dynamic E-Commerce System

A flexible console-based e-commerce system built in Java that extends the original requirements with role-based access control and comprehensive product management capabilities.

## Project Overview

This project was developed as part of the **Fawry Quantum Internship Challenge** with enhanced functionality beyond the original specifications. While the original PDF outlined basic e-commerce features, this implementation introduces a more dynamic and flexible system with separate **Admin** and **Customer** panels, making it suitable for real-world e-commerce scenarios.

## Key Enhancements & Design Decisions

###  **Flexible Product Management**
The original PDF didn't specify exact product types, so this implementation creates a flexible system that can handle:
- **Any product type** with customizable attributes
- **Expirable vs Non-expirable** products
- **Shippable vs Digital** products
- **Dynamic product creation** through admin interface

###  **Role-Based Access Control**
- **Admin Panel**: Full product management capabilities
- **Customer Panel**: Shopping and checkout functionality
- **Separation of concerns** for better security and user experience

###  **File-Based Database System**
- Persistent data storage using text files
- No external database dependencies
- Easy to understand and modify data structure

## Features

###  **Admin Panel**
- **Add New Products**: Create products with comprehensive details
  - Product name, price, and quantity
  - Expiry date configuration (optional)
  - Shipping requirements and weight specification
  - Real-time validation and error handling
- **Edit Existing Products**: Modify any product attribute
  - Update prices, quantities, names
  - Change expiry dates and shipping requirements
  - Flexible editing system for all product types
- **View Product Inventory**: Complete product catalog with details
  - Stock levels, expiry status, shipping info
  - Organized display with all relevant information
- **Remove Products**: Delete products from the system
  - Safe removal with confirmation
  - Automatic inventory updates

###  **Customer Panel**
- **Browse Products**: View available products with filtering
  - Only shows in-stock, non-expired items
  - Clear pricing and availability information
- **Shopping Cart Management**: 
  - Add products with quantity validation
  - View cart contents and running subtotal
  - Automatic stock checking
- **Secure Checkout Process**:
  - Comprehensive validation (stock, expiry, balance)
  - Shipping calculation for physical products
  - Detailed receipt generation
  - Real-time inventory updates

###  **Smart Product System**
- **Expirable Products**: Automatic expiry date tracking
  - Examples: Cheese, Biscuits, Milk, Bread
  - Prevents sale of expired items
- **Non-Expirable Products**: Permanent inventory items
  - Examples: Electronics, Digital products
  - No expiry date management needed
- **Shipping Integration**: Weight-based shipping calculations
  - Physical products require shipping
  - Digital products skip shipping process
- **Flexible Weight System**: Customizable shipping weights

## Technical Architecture

###  **Object-Oriented Design**
- **Abstract Product Class**: Base functionality for all products
- **Inheritance**: Specialized classes for different product types
- **Interface Implementation**: Shippable interface for shipping logic
- **Polymorphism**: Flexible product handling

###  **File-Based Database**
- **products.txt**: Product inventory with full details
- **customers.txt**: Customer information and balances
- **Automatic persistence**: Data saved after every operation
- **Human-readable format**: Easy to inspect and modify

###  **Robust Validation System**
- **Input validation**: All user inputs are validated
- **Business logic validation**: Stock levels, expiry dates, balances
- **Error handling**: Comprehensive error messages and recovery
- **Edge case handling**: Empty carts, insufficient funds, expired products

## Project Structure

```
dynamic-ecommerce-system/
├── src/
│   ├── ECommerceSystem.java      # Main application with console interface
│   ├── Product.java              # Abstract base class for all products
│   ├── ExpirableProduct.java     # Products with expiry date tracking
│   ├── NonExpirableProduct.java  # Products without expiry concerns
│   ├── Shippable.java            # Interface for items requiring shipping
│   ├── Customer.java             # Customer data and balance management
│   ├── Cart.java                 # Shopping cart with validation logic
│   ├── CartItem.java             # Individual cart item representation
│   ├── ShippingService.java      # Shipping calculation and processing
│   └── DatabaseManager.java      # File-based data persistence layer
│   ├── products.txt              # Product inventory database
│   └── customers.txt             # Customer information database
├── README.md
└── .gitignore
```

## Installation & Setup

### Prerequisites
- Java 8 or higher
- Command line interface

### Quick Start
1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/dynamic-ecommerce-system.git
   cd dynamic-ecommerce-system
   ```

2. **Compile the application**
   ```bash
   javac src/*.java
   ```

3. **Run the system**
   ```bash
   java -cp src ECommerceSystem
   ```

## Usage Guide

###  **Admin Operations**
1. Select **"Admin Panel"** from the main menu
2. **Add Products**: Create new inventory items with all specifications
3. **Edit Products**: Modify existing products (price updates, stock refills, etc.)
4. **View Inventory**: Review complete product catalog
5. **Remove Products**: Delete discontinued items

###  **Customer Operations**
1. Select **"Customer Panel"** from the main menu
2. **Choose Customer**: Select from available customer accounts
3. **Browse Products**: View available items with current stock
4. **Add to Cart**: Select products and quantities
5. **Checkout**: Complete purchase with automatic receipt generation

###  **Sample Checkout Output**
```
** Shipment notice **
2x Cheese 400g
1x TV 8000g
Total package weight 8.4kg

** Checkout receipt **
2x Cheese 31.98
1x TV 599.99
----------------------
Subtotal 631.97
Shipping 84.00
Amount 715.97
Customer balance after payment: $784.03
END.
```

## Data Management

###  **File Formats**

**Products Database (products.txt)**
```
TYPE|NAME|PRICE|QUANTITY|REQUIRES_SHIPPING|[EXPIRY_DATE]|WEIGHT
```

**Customers Database (customers.txt)**
```
CUSTOMER_NAME|BALANCE
```

###  **Sample Data Included**
- **15+ diverse products** covering different categories
- **8 customer accounts** with varying balance levels
- **Mix of product types** for comprehensive testing

## Advanced Features

###  **Smart Validation**
- **Stock Management**: Prevents overselling
- **Expiry Tracking**: Blocks expired product sales
- **Balance Verification**: Ensures sufficient customer funds
- **Input Sanitization**: Robust input validation

###  **Shipping Integration**
- **Weight-based calculations**: Realistic shipping costs
- **Shipping notices**: Detailed packing information
- **Digital product handling**: Skips shipping for non-physical items

### **Data Persistence**
- **Automatic saving**: All changes immediately persisted
- **Session continuity**: Data maintained between application runs
- **Easy backup**: Simple file-based storage

## Original Requirements Fulfilled

 **Product Definition**: Name, price, quantity support  
 **Expiry Management**: Cheese, Biscuits, and other perishables  
 **Shipping Logic**: Weight-based shipping for physical products  
 **Cart Functionality**: Add products with quantity validation  
 **Checkout Process**: Complete validation and receipt generation  
 **Error Handling**: Empty cart, insufficient balance, stock issues  
 **Shipping Service**: Interface-based shipping integration  

## Enhanced Beyond Requirements

 **Admin Panel**: Complete product management system  
 **Customer Panel**: User-friendly shopping interface  
 **File Database**: Persistent data storage  
 **Flexible Architecture**: Easy to extend and modify  
 **Console Interface**: Professional menu-driven system  


## License

This project is part of the **Fawry Quantum Internship Challenge** and serves as a demonstration of Java programming skills, object-oriented design principles, and software architecture best practices.

---
