package aba.puzzle.domain.dto

class NewTaskVO {
    var topic: String = ""
    var allDetails: Collection<DetailVO> = mutableListOf()
}