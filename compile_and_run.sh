#/!bin/bash

# Download the javafx sdk
OPENJFX_VERSION="23.0.1"
OPENJFX_URL="https://download2.gluonhq.com/openjfx/23.0.1/openjfx-${OPENJFX_VERSION}_linux-x64_bin-sdk.zip"
OPENJFX_ZIP="lib/openjfx-${OPENJFX_VERSION}_linux-x64_bin-sdk.zip"

if [ ! -d "./lib/openjfx-${OPENJFX_VERSION}_linux-x64_bin-sdk" ]; then
  echo "Downloading OpenJFX SDK from Gluon..."
  wget -q --show-progress $OPENJFX_URL -P ./lib

  if [ -f "$OPENJFX_ZIP" ]; then
    echo "Extracting OpenJFX SDK..."
    unzip -q $OPENJFX_ZIP -d ./lib/openjfx-23.0.1_linux-x64_bin-sdk
    rm $OPENJFX_ZIP
  else
    echo "Failed to download OpenJFX SDK."
    exit 1
  fi
else
  echo "OpenJFX SDK already downloded."
fi

# Compile
javac -d out -cp .:lib/openjfx-23.0.1_linux-x64_bin-sdk/javafx-sdk-23.0.1/lib/* src/gui/*.java  src/gui/ui/*.java src/Main.java


# copty the assets
cp -r src/assets/* out/assets

# run the code
java --module-path ./lib/openjfx-23.0.1_linux-x64_bin-sdk/javafx-sdk-23.0.1/lib --add-modules javafx.controls,javafx.fxml -cp out Main
