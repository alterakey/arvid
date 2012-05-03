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

import android.content.ContextProvider;

public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider
{
    final static String AUTHORITY = "com.gmail.altakey.arvid.SuggestionProvider";
    final static int MODE = DATABASE_MODE_QUERIES;

    // UriMatcher stuff
    private static final int GET_WORD = 0;
    private static final int SEARCH_SUGGEST = 1;
    private static final UriMatcher sURIMatcher = buildUriMatcher();

    public SearchSuggestionProvider() {
        super();
        setupSuggestions(AUTHORITY, MODE);
    }

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher =  new UriMatcher(UriMatcher.NO_MATCH);
        // to get definitions...
        matcher.addURI(AUTHORITY, "get", GET_WORD);
        // to get suggestions...
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGEST);
        return matcher;
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
            case GET_WORD:
                return getWord(uri);
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    private Cursor getSuggestions(String query) {
      query = query.toLowerCase();
      String[] columns = new String[] {
          BaseColumns._ID,
		  SearchManager.SUGGEST_COLUMN_TEXT_1,
		  SearchManager.SUGGEST_COLUMN_TEXT_2,
          SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
	  };

      return mDictionary.getWordMatches(query, columns);
    }

    private Cursor getWord(Uri uri) {
      String rowId = uri.getLastPathSegment();
      String[] columns = new String[] {
		  SearchManager.SUGGEST_COLUMN_TEXT_1,
		  SearchManager.SUGGEST_COLUMN_TEXT_2
	  }

      return mDictionary.getWord(rowId, columns);
    }


}
