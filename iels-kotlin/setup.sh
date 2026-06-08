#!/bin/bash

# Iels Kotlin - Supabase Setup Script

echo "🚀 Iels Kotlin - Supabase Setup"
echo "=================================="
echo ""

# Check if .env file exists
if [ ! -f ".env" ]; then
    echo "📝 Creating .env file from .env.example..."
    cp .env.example .env
    echo "✓ .env file created"
    echo ""
    echo "⚠️  Please edit .env with your Supabase credentials:"
    echo "   - SUPABASE_URL"
    echo "   - SUPABASE_ANON_KEY"
    echo "   - SUPABASE_SERVICE_KEY"
    echo ""
fi

# Check if .env has placeholder values
if grep -q "your_anon_key_here" .env; then
    echo "⚠️  .env still contains placeholder values"
    echo "   Please update with your actual Supabase credentials"
    echo ""
fi

echo "📦 Building project..."
./gradlew clean build

echo ""
echo "✓ Setup complete!"
echo ""
echo "Next steps:"
echo "1. Update .env with your Supabase credentials"
echo "2. Create tables in Supabase using the SQL provided in SUPABASE_INTEGRATION.md"
echo "3. Run the application: ./gradlew run"
