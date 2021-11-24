"""
Tic Tac Toe Player
"""

import math
import copy

X = "X"
O = "O"
EMPTY = None


def initial_state():
    """
    Returns starting state of the board.
    """
    return [[EMPTY, EMPTY, EMPTY],
            [EMPTY, EMPTY, EMPTY],
            [EMPTY, EMPTY, EMPTY]]


def player(board):
    """
    Returns player who has the next turn on a board.
    """
    #we check for the number of empty space. If there is an even no of spaces then it's O's turn else X's turn
    empty_spaces = 0
    for row in range(3):
        for column in range(3):
            if board[row][column] == EMPTY:
                empty_spaces += 1
    if empty_spaces % 2 == 0:
        return O
    else:
        return X


def actions(board):
    """
    Returns set of all possible actions (i, j) available on the board.
    """
    action_set = set()
    for row in range(3):
        for column in range(3):
            if board[row][column] == EMPTY:
                action_set.add((row, column))
    return action_set

def result(board, action):
    """
    Returns the board that results from making move (i, j) on the board.
    """
    row, column = action

    #check if the indexes are out of bounds
    if row < 0 or row > 2 or column < 0 or column > 2:
        raise Exception("Not a valid play area")

    #check if the board position is occupied
    elif board[row][column] != EMPTY:
        raise Exception("Place is not empty")

    else:
        #make a deep copy of the board so that the original board remains unchanged
        player_marker = player(board)
        board_copy = copy.deepcopy(board)
        board_copy[row][column] = player_marker
        return board_copy


def winner(board):
    """
    Returns the winner of the game, if there is one.
    """
    #checking all the win conditions
    if ((board[0][0] == board[0][1] == board[0][2] == X) or
            (board[1][0] == board[1][1] == board[1][2] == X) or
            (board[2][0] == board[2][1] == board[2][2] == X) or
            (board[0][0] == board[1][0] == board[2][0] == X) or
            (board[0][1] == board[1][1] == board[2][1] == X) or
            (board[0][2] == board[1][2] == board[2][2] == X) or
            (board[0][0] == board[1][1] == board[2][2] == X) or
            (board[2][0] == board[1][1] == board[0][2] == X)):
        return X

    elif ((board[0][0] == board[0][1] == board[0][2] == O) or
          (board[1][0] == board[1][1] == board[1][2] == O) or
          (board[2][0] == board[2][1] == board[2][2] == O) or
          (board[0][0] == board[1][0] == board[2][0] == O) or
          (board[0][1] == board[1][1] == board[2][1] == O) or
          (board[0][2] == board[1][2] == board[2][2] == O) or
          (board[0][0] == board[1][1] == board[2][2] == O) or
          (board[2][0] == board[1][1] == board[0][2] == O)):
        return O

    else:
        return None


def terminal(board):
    """
    Returns True if game is over, False otherwise.
    """
    if winner(board) != None:
        return True

    for i in range(3):
        for j in range(3):
            if board[i][j] == EMPTY:
                return False
    return True

def utility(board):
    """
    Returns 1 if X has won the game, -1 if O has won, 0 otherwise.
    """
    utility = 0
    if winner(board) == X:
        utility = 1
    elif winner(board) == O:
        utility = -1
    else:
        utility = 0

    return utility

def max_value(board):
    """
    Tries to maximize the score
    """
    if terminal(board):
        return utility(board)
    else:
        v = -math.inf
        for action in actions(board):
            v = max(v, min_value(result(board, action)))
        return v

def min_value(board):
    """
    Tries to minimize the score
    """
    if terminal(board):
        return utility(board)
    else:
        v = math.inf
        for action in actions(board):
            v = min(v, max_value(result(board, action)))
        return v

def minimax(board):
    """
    Returns the optimal action for the current player on the board.
    """
    if terminal(board):
        return None
    else:
        optimal_action = None
        if player(board) == X:
            v = -math.inf
            for action in actions(board):
                minval = min_value(result(board, action))
                if v < minval:
                    v = minval
                    optimal_action = action
        elif player(board) == O:
            v = math.inf
            for action in actions(board):
                maxval = max_value(result(board, action))
                if v > maxval:
                    v = maxval
                    optimal_action = action
        return optimal_action
