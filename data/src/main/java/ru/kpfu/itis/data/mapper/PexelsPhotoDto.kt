package ru.kpfu.itis.data.mapper

import ru.kpfu.itis.data.dto.PexelsPhotoDto
import ru.kpfu.itis.domain.model.Photo
import ru.kpfu.itis.domain.model.PhotoId

internal fun PexelsPhotoDto.toDomain(): Photo = Photo(
    id = PhotoId(id.toString()),
    url = src.original,
    photographer = photographer,
    tags = emptyList(),
    liked = liked
)

internal fun List<PexelsPhotoDto>.toDomain(): List<Photo> = map(PexelsPhotoDto::toDomain)