Piqu
====

Really simple Twitter client application for Android

v2.0 - Major bug fixes
* Tweets can no longer be more than 140 characters nor empty
* Created BaseActivity and BaseListActivity to prevent duplicate code
* Logout actually works now
* Better classing structure
* Removed useless imports
* Removed auto generated comments
* Removed hardcoded numberic/string values
* There can no longer be multiple instances of the same activity
* Application actually works now on 3.0+ Android (thanks to ActionBarSherlock)
* Search queries can no longer be empty
* All network operations have been moved outside of UI threa, into Asynctasks

v1.0 - Basic functionality achieved
* Auth
* Search
* Tweet
* Timeline