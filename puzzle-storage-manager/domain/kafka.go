package domain

type PuzzleState struct {
	Id              *int                   `json:"id"`
	IsCompleted     bool                   `json:"completed"`
	PuzzleConfigDto *PuzzleConfigDto       `json:"puzzleConfigDto"`
	Coverage        []*PuzzleStateEntryDto `json:"coverage"`
}

type PuzzleConfigDto struct {
	Id            *int               `json:"id"`
	ExtId         *string            `json:"extId"`
	PuzzleMapDto  *PuzzleMapDto      `json:"puzzleMap"`
	PuzzleDetails []*PuzzleDetailDto `json:"puzzleDetails"`
}

type PuzzleDetailDto struct {
	Id               *int         `json:"id"`
	ExtId            *int         `json:"extId"`
	Left             *BallSideDto `json:"left"`
	Upper            *BallSideDto `json:"upper"`
	Right            *BallSideDto `json:"right"`
	Lower            *BallSideDto `json:"lower"`
	AllowedRotations []int        `json:"allowedRotations"`
}

type BallSideDto struct {
	Color *ColorDto    `json:"color"`
	Side  *BallPartDto `json:"side"`
}

type ColorDto struct {
	Color string `json:"color"`
}
type BallPartDto struct {
	Side string `json:"side"`
}

type PuzzleMapDto struct {
	PuzzleFields []*PuzzleFieldDto `json:"puzzleFields"`
}

type PuzzleFieldDto struct {
	Id     *int `json:"id"`
	ShiftX int  `json:"shiftX"`
	ShiftY int  `json:"shiftY"`
}

type PuzzleStateEntryDto struct {
	PuzzleFieldDto              *PuzzleFieldDto              `json:"puzzleFieldDto"`
	PuzzleDetailWithRotationDto *PuzzleDetailWithRotationDto `json:"puzzleDetailWithRotationDto"`
}

type PuzzleDetailWithRotationDto struct {
	Detail   PuzzleDetailDto `json:"detail"`
	Rotation int             `json:"rotation"`
}
