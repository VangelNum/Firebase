package com.zxcursed.wallpaper.feature_developer.domain.use_cases

import javax.inject.Inject

data class DeveloperUseCase @Inject constructor(
    val addUsersPhotosDevUseCase: AddUsersPhotosDevUseCase,
    val deleteUsersPhotosUseCase: DeleteUsersPhotosUseCase,
    val getUsersPhotosUseCase: GetUsersPhotosUseCase,
    val deleteUsersPhotosFromFirestoreUseCase: DeleteUsersPhotosFromFirestoreUseCase
)