# Supabase Integration Quick Reference

## File Structure

```
edudesk-kotlin/
├── .env                                    # Environment variables (NEVER commit)
├── .env.example                            # Template for environment variables
├── build.gradle.kts                        # Updated with Supabase dependencies
├── setup.sh / setup.bat                    # Setup scripts
├── src/main/kotlin/com/edudesk/
│   ├── config/
│   │   ├── SupabaseConfig.kt              # Configuration loader
│   │   └── SupabaseClient.kt              # Client manager
│   ├── database/
│   │   └── DatabaseSetup.kt               # Updated with Supabase support
│   ├── models/
│   │   └── SupabaseModels.kt              # Supabase data models
│   └── services/
│       ├── AuthService.kt                 # Updated auth service
│       ├── SupabaseUserService.kt         # User operations
│       ├── SupabaseCourseService.kt       # Course operations
│       ├── SupabaseAssignmentService.kt   # Assignment operations
│       └── SupabaseEnrollmentService.kt   # Enrollment operations
```

## API Key Locations

To find your API keys in Supabase:

1. Go to [https://supabase.com](https://supabase.com)
2. Open your project
3. Go to **Settings** → **API**
4. Copy the values:
   - **Project URL** → `SUPABASE_URL`
   - **anon public** → `SUPABASE_ANON_KEY`
   - **service_role secret** → `SUPABASE_SERVICE_KEY`

## Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| `.env` file not found | Run `setup.sh` or `setup.bat` |
| "Connection refused" | Check your Supabase URL and internet connection |
| "Invalid API key" | Verify your keys are correct in `.env` |
| Serialization errors | Ensure your Supabase table columns match model properties |

## Using with Compose Screens

When using Supabase services in Compose screens, use `LaunchedEffect` for async operations:

```kotlin
@Composable
fun MyScreen() {
    val userService = SupabaseUserService()
    var user by remember { mutableStateOf<SupabaseUser?>(null) }
    
    LaunchedEffect(Unit) {
        user = userService.getUserById("user-id")
    }
    
    if (user != null) {
        Text("Hello, ${user?.name}")
    }
}
```

## Database Schema

All tables are in public schema. Common fields:
- `id` (UUID, Primary Key)
- `created_at` (Timestamp)
- All other fields are string/int based on requirements

## Security Notes

1. **Never** share `.env` file
2. **Never** commit `.env` to Git
3. **Never** expose `SUPABASE_SERVICE_KEY` in client code
4. **Always** use environment variables for sensitive data
5. **Enable** Row Level Security (RLS) for all tables
