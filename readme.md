# Exchange Simulator Project

## Overview
The Exchange Simulator is a Java-based application that simulates the core functionalities of a cryptocurrency exchange. It allows users to interact with a client/server architecture to:
- Manage orders (market and limit orders, cancellations).
- View a real-time order book.
- Analyze price trends with real-time charts and statistics.
- Simulate market fluctuations.
- Manage virtual user accounts and receive real-time notifications.

## Key Features
1. **Order Management**: Place market and limit orders.
2. **Real-Time Order Book**: Displays buy and sell orders, automatically updated.
3. **Market Simulation**: Simulates price changes for cryptocurrencies.
4. **Charts and Statistics**: Real-time price trends and trading data.
5. **User Account Management**: Virtual balances for fiat and cryptocurrencies.
6. **Real-Time Notifications**: Alerts for order execution and errors.


### Build the project
```bash
mvn clean install
```

### Run the project 

[//]: # (- on **macOs&#40;aarch64&#41;**)

[//]: # (    ```)

[//]: # (    mvn exec:java -Pjavafx.platform.macosx.aarch64)

[//]: # (    ```)

[//]: # (- on **macOs&#40;x64&#41;**)

[//]: # (    ```)

[//]: # (    mvn exec:java -Pjavafx.platform.macosx.x64)

[//]: # (    ```)

[//]: # (- on **Windows**)

[//]: # (    ```)

[//]: # (    mvn exec:java -Pjava.platform.windows)

[//]: # (    ```)

[//]: # (- on **Linux**)

[//]: # (    ```)

[//]: # (    mvn exec:java -Pjava.platform.linux)

[//]: # (    ```)
  
```commandline
mvn exec:java
```
