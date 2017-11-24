# Building Dropbox

A replica of the cloud storage service Dropbox is emulated in this application.
JDO parent child relationship along with GAEs blobstore is used to create this application. 

The application is capable of the following
1. Each user's dropbox is segregated from all other user's dropbox.
2. A fully functional directory structure that can handle an arbitrary levels of directories.
3. Repeat directory names are not permitted.
4. The addition and deletion of files.
5. The download of files.