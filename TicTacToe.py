#Creating a 2 player TicTacToe game which runs on the command line
import random

#Creating supporting functions

#1 Creating a function which displays a TicTacToe board
def display_board(board):

    '''
    prints a basic tictactoe board
    '''

    print('\n'*100) #To clear the screen
    print(board[7] + ' | ' +  board[8] + ' | ' + board[9])
    print('---------')
    print(board[4] + ' | ' +  board[5] + ' | ' + board[6])
    print('---------')
    print(board[1] + ' | ' +  board[2] + ' | ' + board[3])

#2 Creating a function to accept player's marker
def player_input():

    '''
    returns a tuple containing the two markers
    '''

    while True: #keeps asking for the correct input
        marker = input("Player 1 do you want to be 'X' or 'O'? ")
        if marker.upper() == 'X':
            return ('X','O')
        elif marker.upper() == 'O':
            return ('O','X')
        else:
            print("Please enter 'X' or 'O'")

#3 Creating a function which places a marker at a chosen position
def place_marker(board,marker,position):

    '''
    places the player's marker at the chosen position
    '''

    board[position] = marker
    return board
    
     
#4 Creating a function to check if a certain slot on the board is empty
def space_check(board, position):

    '''
    returns a boolean depending on whether the chosen spot on the board is empty or full
    '''

    return board[position] == ' '

#5 Creating a function which accepts position of the player and returns if it is an empty position
def player_choice(board):

    '''
    returns the position if it is valid and is empty
    '''

    while True: #Keeps asking the player for the correct input
        position = input("Enter the position where you wan to place your marker (1-9): ")

        #Checking to see if the input contains digits or not
        try:
            position = int(position)
        except:
            print("Please enter a number (1-9)")
            continue

        if position in range(1,10) and space_check(board,position):
            return position
        else:
            print("Please enter a number in the range 1-9 ")

#6 Creating a function which checks if the board is full
def full_board_check(board):

    '''
    returns true if the board is full and vice-versa
    '''

    #Looping over all positions in the board
    for i in range(1,10):
        if board[i] == ' ':
            return False
    return True

#7 Creating a function which chooses a random player
def choose_player():

    '''
    returns a random player among 2
    '''

    player = random.randint(1,2)
    if player == 1:
        return 'Player 1'
    else:
        return 'Player 2'

#8 Creating a function which checks if someone has won
def win_check(board,marker):

    '''
    checks all winning conditions
    '''

    return (board[1] == board[2] == board[3] == marker or #horizontally across
    board[4] == board[5] == board[6] == marker or #horizontally across
    board[7] == board[8] == board[9] == marker or #horizontally across
    board[1] == board[4] == board[7] == marker or #vertically across
    board[2] == board[5] == board[8] == marker or #vertically across
    board[3] == board[6] == board[9] == marker or #vertically across
    board[1] == board[5] == board[9] == marker or #diagonally across
    board[7] == board[5] == board[3] == marker) #diagonally across

#9 Creating a function which asks the players if they want to play again
def replay():

    '''
    returns a booloan depending on the players desire to play again
    '''

    choice = input("Do you want to play again? Enter Yes or No: ")
    return choice[0].lower() == 'y'

#10 Main fuction which runs the game
def play():

    '''
    putting all pieces together to run the game
    '''

    #Greet the players
    print("Welcome to TicTacToe!")

    #While loop to keep the game going
    while True:

        #initializing the board
        game_board = [' ']*10

        #assigning markers to the players
        Player1_marker, Player2_marker = player_input()

        #choosing which player goes first
        turn = choose_player()
        print(turn,'will go first')

        #asking whether to proceed with the game
        play = input("Ready to play? ")
        if play[0].lower() == 'y':
            game_on = True
        else:
            game_on = False

        #playing the game        
        while game_on:

            #player 1's turn
            if turn  == 'Player 1':
                display_board(game_board)

                #assigning the marker to the position on the board
                position = player_choice(game_board) 
                place_marker(game_board,Player1_marker,position)

                #checking if they have won
                if win_check(game_board,Player1_marker):
                    display_board(game_board)
                    print("Player 1 Wins!")
                    game_on = False
                else:

                    #checking if there is a tie
                    if full_board_check(game_board):
                        display_board(game_board)
                        print("Tie Game!")
                        game_on = False
                    else:
                        turn = 'Player 2'
            else:

                #player 2's turn
                display_board(game_board)

                #assigning the marker to the position on the board
                position = player_choice(game_board)
                place_marker(game_board,Player2_marker,position)

                #checking if they have won
                if win_check(game_board,Player2_marker):
                    display_board(game_board)
                    print("Player 2 Wins!")
                    game_on = False
                else:

                    #checking if there is a tie
                    if full_board_check(game_board):
                        display_board(game_board)
                        print("Tie Game!")
                        game_on = False
                    else:
                        turn = 'Player 1'

        #asking if the players want to play again
        if not replay():
            break

play()

#Thank You! Programmed by Rik Ganguli Biswas