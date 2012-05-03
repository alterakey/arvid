/*
 * Copyright (C) 2011 Takahiro Yoshimura.  All rights reserved.
 */

/*
 * Largely borrowed from API Demos:
 *
 * Copyright (C) 2008 The Android Open Source Project
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

package com.gmail.altakey.arvid;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.provider.BaseColumns;
import android.app.SearchManager;
import android.net.Uri;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

public class SearchSuggestionProvider extends ContentProvider
{
    final static String AUTHORITY = "com.gmail.altakey.arvid.SuggestionProvider";

    // UriMatcher stuff
    private static final int GET_WORD = 0;
    private static final int SEARCH_SUGGEST = 1;
    private static final UriMatcher sURIMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher =  new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGEST);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Use the UriMatcher to see what kind of query we have and format the db query accordingly
        switch (sURIMatcher.match(uri)) {
            case SEARCH_SUGGEST:
                if (selectionArgs == null) {
                  throw new IllegalArgumentException(
                      "selectionArgs must be provided for the Uri: " + uri);
                }
                return getSuggestions(selectionArgs[0]);
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    private Cursor getSuggestions(String query) {
      query = query.toLowerCase();
      String[] columns = new String[] {
          BaseColumns._ID,
		  SearchManager.SUGGEST_COLUMN_TEXT_1,
		  SearchManager.SUGGEST_COLUMN_TEXT_2
	  };

      MatrixCursor out = new MatrixCursor(columns);
      out.addRow(new Object[] {1, "test 1", "spamish test"});
      out.addRow(new Object[] {2, "test 2", "spamish test 2"});
      out.addRow(new Object[] {3, "test 3", "spamish test 3"});
      out.addRow(new Object[] {4, "test 4", "spamish test 4"});
      out.addRow(new Object[] {5, "test 5", "spamish test 5"});
      out.addRow(new Object[] {6, "test 6", "spamish test 6"});
      out.addRow(new Object[] {7, "test 7", "spamish test 7"});
      out.addRow(new Object[] {8, "test 8", "spamish test 8"});
      out.addRow(new Object[] {9, "test 9", "spamish test 9"});
      out.addRow(new Object[] {10, "test 10", "spamish test 10"});
      out.addRow(new Object[] {11, "test 11", "spamish test 11"});
      out.addRow(new Object[] {12, "test 12", "spamish test 12"});
      out.addRow(new Object[] {13, "test 13", "spamish test 13"});
      out.addRow(new Object[] {14, "test 14", "spamish test 14"});
      out.addRow(new Object[] {15, "test 15", "spamish test 15"});
      out.addRow(new Object[] {16, "test 16", "spamish test 16"});
      Log.d("SSP", String.format("rows: %d", out.getCount()));

      out.moveToFirst();

      return out;
    }

   @Override
   public String getType(Uri uri) {
       switch (sURIMatcher.match(uri)) {
       case SEARCH_SUGGEST:
           return SearchManager.SUGGEST_MIME_TYPE;
       default:
           throw new IllegalArgumentException("Unknown URL " + uri);
       }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

}
