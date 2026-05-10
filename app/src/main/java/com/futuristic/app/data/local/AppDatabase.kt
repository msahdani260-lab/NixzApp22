package com.futuristic.app.data.local

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

// --- Entity ---
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int = 1, // Single user for this demo
    val name: String,
    val email: String,
    val token: String
)

// --- DAO ---
@Dao
interface UserDao {
    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun clearSession()
}

// --- Database Manager ---
@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "futuristic_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
