# Supabase Integration - Troubleshooting & Best Practices

## 🐛 Troubleshooting Guide

### Problem: "SUPABASE_URL not set in .env"
**Solution:**
1. Verify `.env` file exists in project root
2. Check file contains `SUPABASE_URL=https://...`
3. Run `setup.sh` or `setup.bat` to regenerate
4. Ensure file is not saved as `.env.txt` or similar

### Problem: "Connection timeout"
**Solution:**
1. Check internet connection
2. Verify Supabase URL is correct (should start with `https://`)
3. Check if project is active in Supabase dashboard
4. Verify there are no firewall restrictions
5. Try again after a few seconds (might be temporary)

### Problem: "Invalid API key"
**Solution:**
1. Copy keys again from Supabase dashboard
2. Verify there are no extra spaces in `.env`
3. Don't remove `=` or quotes if used
4. For special characters, they may need URL encoding

### Problem: Build fails with "Could not find org.jetbrains.kotlinx:kotlinx-coroutines-core"
**Solution:**
1. Sync Gradle: `./gradlew --refresh-dependencies`
2. Check internet connection
3. Check if Maven Central is accessible
4. Try building again: `./gradlew clean build`

### Problem: "decodeList is not resolved"
**Solution:**
1. Add serialization plugin to build.gradle.kts:
   ```kotlin
   plugins {
       kotlin("plugin.serialization") version "2.0.0"
   }
   ```
2. Rebuild project
3. Ensure models have `@Serializable` annotation

### Problem: Data not updating in screens
**Solution:**
1. Verify `LaunchedEffect` is used correctly:
   ```kotlin
   LaunchedEffect(Unit) { // ← Use Unit for initial load
       data = service.fetchData()
   }
   ```
2. Add dependency tracking:
   ```kotlin
   LaunchedEffect(userId) { // ← Rerun when userId changes
       user = service.getUserById(userId)
   }
   ```
3. Check Row Level Security policies in Supabase

## ✅ Best Practices

### 1. Error Handling
```kotlin
// ✅ Good - Silent fallback with logging
val users = userService.getAllUsers() // Returns empty list on error

// ❌ Bad - Unhandled exceptions
val users = SupabaseClientManager.database.from("users")
    .select()
    .decodeList<User>() // May crash
```

### 2. Async Operations in Compose
```kotlin
// ✅ Good - Using LaunchedEffect
@Composable
fun UserList(userId: String) {
    var user by remember { mutableStateOf<SupabaseUser?>(null) }
    
    LaunchedEffect(userId) {
        user = userService.getUserById(userId)
    }
}

// ❌ Bad - Direct suspend call in Composable
@Composable
fun UserList(userId: String) {
    val user = userService.getUserById(userId) // Compilation error!
}
```

### 3. Security - Never Expose Service Key
```kotlin
// ✅ Good - Use anon key for client operations
val client = SupabaseClientManager.client // Uses ANON_KEY

// ❌ Bad - Using service key directly
val client = createSupabaseClient(
    supabaseKey = SupabaseConfig.serviceKey // DON'T USE IN UI!
)
```

### 4. Session Management
```kotlin
// ✅ Good - Use SessionManager
SessionManager.setSupabaseUser(user)
Text("Hello, ${SessionManager.currentSupabaseUser?.name}")

// ❌ Bad - Storing user in multiple places
var localUser by remember { mutableStateOf<SupabaseUser?>(null) }
// Now you have two sources of truth!
```

### 5. Loading States
```kotlin
// ✅ Good - Show feedback during loading
var isLoading by remember { mutableStateOf(true) }

LaunchedEffect(Unit) {
    isLoading = true
    data = service.fetchData()
    isLoading = false
}

if (isLoading) CircularProgressIndicator()
```

### 6. Database Queries with Filters
```kotlin
// ✅ Good - Specific queries
val instructorCourses = courseService.getCoursesByInstructor(instructorId)

// ❌ Bad - Inefficient loading all then filtering
val all = courseService.getAllCourses()
val filtered = all.filter { it.instructorId == instructorId }
```

## 🔐 Security Checklist

- [ ] `.env` added to `.gitignore`
- [ ] Never committed `.env` to Git
- [ ] API keys rotated after any exposure
- [ ] Row Level Security enabled on all tables
- [ ] RLS policies written for each table
- [ ] Service key not used in client code
- [ ] Anon key only has necessary permissions
- [ ] No hardcoded credentials anywhere
- [ ] `.env.example` contains only placeholders

## 🎯 Performance Tips

1. **Use Specific Queries**
   ```kotlin
   // Fast - Specific ID
   userService.getUserById(id)
   
   // Slower - Filter all
   userService.getAllUsers().find { it.id == id }
   ```

2. **Cache User Data**
   ```kotlin
   // Load once, reuse
   val user = SessionManager.currentSupabaseUser
   ```

3. **Batch Operations**
   ```kotlin
   // Good for bulk updates
   courses.forEach { updateCourse(it.id, updates) }
   ```

4. **Use Pagination for Large Lists**
   ```kotlin
   // Consider pagination for thousands of records
   val page1 = service.getAllCourses().take(10)
   val page2 = service.getAllCourses().drop(10).take(10)
   ```

## 📦 Environment Setup Progress Tracking

Run these commands to verify setup:

```bash
# Check .env exists and has values
cat .env

# Test Gradle build
./gradlew clean build

# Verify dependencies
./gradlew dependencies

# Check for Kotlin errors
./gradlew kotlinc
```

## 🚀 Deployment Considerations

1. **Production .env**
   - Never include real .env in Docker containers
   - Use container secrets/environment variables
   - Rotate API keys regularly

2. **Database Backups**
   - Supabase handles backups automatically
   - Configure daily backups if available
   - Test restore procedures

3. **Monitoring**
   - Monitor Supabase logs
   - Set up error tracking (Sentry, etc)
   - Track API rate limits

4. **Scaling**
   - Supabase auto-scales databases
   - Monitor connection limits
   - Consider connection pooling for high volumes

---

**Last Updated**: April 2026
**Version**: 1.0
