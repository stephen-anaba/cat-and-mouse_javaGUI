#!/bin/bash

# Enable strict error handling
set -e

# Define cleanup function
cleanup() {
    echo "Cleaning up .class files..."
    rm -f *.class
    echo "Done."
}

# Register the cleanup function to be called on EXIT (normal or Ctrl+C)
trap cleanup EXIT

# Compile Java files
echo "Compiling Java files..."
javac Main.java Board.java

# Run the program
echo "Running Cat-and-Mouse GUI..."
java Main

