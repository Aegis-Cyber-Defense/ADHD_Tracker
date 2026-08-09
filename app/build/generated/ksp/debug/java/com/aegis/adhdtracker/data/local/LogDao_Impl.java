package com.aegis.adhdtracker.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LogDao_Impl implements LogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DailyLogEntity> __insertionAdapterOfDailyLogEntity;

  public LogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDailyLogEntity = new EntityInsertionAdapter<DailyLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `daily_logs` (`id`,`timestamp`,`foodIntake`,`emotionState`,`energyLevel`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyLogEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTimestamp());
        statement.bindString(3, entity.getFoodIntake());
        statement.bindString(4, entity.getEmotionState());
        statement.bindLong(5, entity.getEnergyLevel());
      }
    };
  }

  @Override
  public Object insertLog(final DailyLogEntity log, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDailyLogEntity.insert(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<DailyLogEntity>> getAllLogs() {
    final String _sql = "SELECT * FROM daily_logs ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_logs"}, new Callable<List<DailyLogEntity>>() {
      @Override
      @NonNull
      public List<DailyLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfFoodIntake = CursorUtil.getColumnIndexOrThrow(_cursor, "foodIntake");
          final int _cursorIndexOfEmotionState = CursorUtil.getColumnIndexOrThrow(_cursor, "emotionState");
          final int _cursorIndexOfEnergyLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "energyLevel");
          final List<DailyLogEntity> _result = new ArrayList<DailyLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyLogEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpFoodIntake;
            _tmpFoodIntake = _cursor.getString(_cursorIndexOfFoodIntake);
            final String _tmpEmotionState;
            _tmpEmotionState = _cursor.getString(_cursorIndexOfEmotionState);
            final int _tmpEnergyLevel;
            _tmpEnergyLevel = _cursor.getInt(_cursorIndexOfEnergyLevel);
            _item = new DailyLogEntity(_tmpId,_tmpTimestamp,_tmpFoodIntake,_tmpEmotionState,_tmpEnergyLevel);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
