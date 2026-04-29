package ru.kpfu.itis.data.mapper

import ru.kpfu.itis.data.database.entity.PhotoEntity
import ru.kpfu.itis.data.dto.PexelsPhotoDto
import ru.kpfu.itis.domain.model.Photo
import ru.kpfu.itis.domain.model.PhotoId

internal fun PexelsPhotoDto.toEntity(): PhotoEntity = PhotoEntity(
    id = id.toString(),
    url = src.original,
    photographer = photographer,
    tagsJson = "",
    liked = liked
)

internal fun PexelsPhotoDto.toDomain(): Photo = Photo(
    id = PhotoId(id.toString()),
    url = src.original,
    photographer = photographer,
    tags = emptyList(),
    liked = liked,
    downloadedAt = null
)

internal fun List<PexelsPhotoDto>.toDomainList(): List<Photo> = map(PexelsPhotoDto::toDomain)

internal fun List<PexelsPhotoDto>.toEntityList(): List<PhotoEntity> = map(PexelsPhotoDto::toEntity)

internal fun PhotoEntity.toDomain(): Photo = Photo(
    id = PhotoId(id),
    url = url,
    photographer = photographer,
    tags = emptyList(),
    liked = liked,
    downloadedAt = null
)

internal fun List<PhotoEntity>.toDomainListFromCache(): List<Photo> = map(PhotoEntity::toDomain)