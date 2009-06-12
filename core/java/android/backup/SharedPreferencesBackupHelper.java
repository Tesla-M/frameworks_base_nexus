/*
 * Copyright (C) 2009 The Android Open Source Project
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

package android.backup;

import android.content.Context;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;

/** @hide */
public class SharedPreferencesBackupHelper {
    private Context mContext;
    private String mKeyPrefix;

    public SharedPreferencesBackupHelper(Context context) {
        mContext = context;
    }

    public SharedPreferencesBackupHelper(Context context, String keyPrefix) {
        mContext = context;
        mKeyPrefix = keyPrefix;
    }
    
    public void performBackup(ParcelFileDescriptor oldSnapshot, ParcelFileDescriptor newSnapshot,
            BackupDataOutput data, String[] prefGroups) {
        Context context = mContext;
        
        // make filenames for the prefGroups
        final int N = prefGroups.length;
        String[] files = new String[N];
        for (int i=0; i<N; i++) {
            files[i] = context.getSharedPrefsFile(prefGroups[i]).getAbsolutePath();
        }

        // make keys if necessary
        String[] keys = FileBackupHelper.makeKeys(mKeyPrefix, prefGroups);

        // go
        FileBackupHelper.performBackup_checked(oldSnapshot, data, newSnapshot, files, prefGroups);
    }
}

