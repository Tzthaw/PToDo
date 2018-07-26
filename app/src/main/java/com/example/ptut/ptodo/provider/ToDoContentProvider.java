/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ptut.ptodo.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ptut.ptodo.model.ToDoItemModel;
import com.example.ptut.ptodo.roomdb.AppDatabase;
import com.example.ptut.ptodo.roomdb.daos.ToDoDao;
import com.example.ptut.ptodo.roomdb.entity.ToDoItems;
import com.example.ptut.ptodo.util.ToDo;

import java.util.ArrayList;



public class ToDoContentProvider extends ContentProvider {

    /** The authority of this content provider. */
    public static final String AUTHORITY = "com.example.ptut.ptodo.provider";

    /** The URI for the  table. */
    public static final Uri URI_TODOITEMS = Uri.parse(
            "content://" + AUTHORITY + "/" + ToDoItems.TABLE_NAME);

    public static final Uri CONTENT_URI=Uri.parse("content://" + AUTHORITY).buildUpon().appendPath(ToDoItems.TABLE_NAME).build();


    private static final int CODE_TODOITEMS_DIR = 1;


    private static final int CODE_TODOITEMS_ITEM = 2;


    /** The URI matcher. */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    ToDoItemModel toDoItemModel;
     long id;



    static {

        MATCHER.addURI(AUTHORITY, ToDoItems.TABLE_NAME, CODE_TODOITEMS_DIR);
        MATCHER.addURI(AUTHORITY, ToDoItems.TABLE_NAME + "/*", CODE_TODOITEMS_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_TODOITEMS_DIR || code == CODE_TODOITEMS_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            ToDoDao toDoDao = AppDatabase.getAppInstance(context).toDoDao();
            final Cursor cursor;
            if (code == CODE_TODOITEMS_DIR) {
                cursor = toDoDao.selectAllCursor();
            } else {
                cursor = toDoDao.selectByIdCursor(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_TODOITEMS_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + ToDoItems.TABLE_NAME;
            case CODE_TODOITEMS_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + ToDoItems.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_TODOITEMS_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
              id = AppDatabase.getAppInstance(context).toDoDao()
                        .addToDoItem(ToDoItems.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_TODOITEMS_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        Uri match=uri;
        switch (MATCHER.match(uri)) {
            case CODE_TODOITEMS_DIR  :
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_TODOITEMS_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = AppDatabase.getAppInstance(context).toDoDao()
                        .deleteById(id);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_TODOITEMS_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_TODOITEMS_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final ToDoItems toDoItems = ToDoItems.fromContentValues(values);
                toDoItems.id = ContentUris.parseId(uri);
                final int count = AppDatabase.getAppInstance(context).toDoDao()
                        .updateItem(toDoItems);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(
            @NonNull ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final Context context = getContext();
        if (context == null) {
            return new ContentProviderResult[0];
        }
        final AppDatabase database = AppDatabase.getAppInstance(context);
        database.beginTransaction();
        try {
            final ContentProviderResult[] result = super.applyBatch(operations);
            database.setTransactionSuccessful();
            return result;
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] valuesArray) {
        switch (MATCHER.match(uri)) {
            case CODE_TODOITEMS_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final AppDatabase database = AppDatabase.getAppInstance(context);
                final ToDoItems[] toDoItems = new ToDoItems[valuesArray.length];
                for (int i = 0; i < valuesArray.length; i++) {
                    toDoItems[i] = ToDoItems.fromContentValues(valuesArray[i]);
                }
                return database.toDoDao().addToDoItems(toDoItems).length;
            case CODE_TODOITEMS_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

}
