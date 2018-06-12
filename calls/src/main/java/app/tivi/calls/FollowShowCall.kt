/*
 * Copyright 2018 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.tivi.calls

import app.tivi.SeasonFetcher
import app.tivi.data.daos.FollowedShowsDao
import app.tivi.data.entities.FollowedShowEntry
import app.tivi.util.AppCoroutineDispatchers
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class FollowShowCall @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val followedShowsDao: FollowedShowsDao,
    private val seasonFetcher: SeasonFetcher
) : Call<Long> {
    override suspend fun doWork(showId: Long) {
        withContext(dispatchers.database) {
            followedShowsDao.insert(FollowedShowEntry(showId = showId))
        }
        // Now refresh seasons
        seasonFetcher.load(showId)
    }
}