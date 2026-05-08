@echo off
REM EduDesk Kotlin - Supabase Setup Script

echo 🚀 EduDesk Kotlin - Supabase Setup
echo ==================================
echo.

REM Check if .env file exists
if not exist ".env" (
    echo 📝 Creating .env file from .env.example...
    copy .env.example .env
    echo ✓ .env file created
    echo.
    echo ⚠️  Please edit .env with your Supabase credentials:
    echo    - SUPABASE_URL
    echo    - SUPABASE_ANON_KEY
    echo    - SUPABASE_SERVICE_KEY
    echo.
)

REM Check if .env has placeholder values
findstr /M "your_anon_key_here" .env >nul
if %ERRORLEVEL% EQU 0 (
    echo ⚠️  .env still contains placeholder values
    echo    Please update with your actual Supabase credentials
    echo.
)

echo 📦 Building project...
call gradlew.bat clean build

echo.
echo ✓ Setup complete!
echo.
echo Next steps:
echo 1. Update .env with your Supabase credentials
echo 2. Create tables in Supabase using the SQL provided in SUPABASE_INTEGRATION.md
echo 3. Run the application: gradlew.bat run
