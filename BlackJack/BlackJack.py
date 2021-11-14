########################################## Command Line BlackJack Program Using Classes #################################################

#import the random module
#one can also import only the shuffle function from the random module

import random

##Declaring global variables

#Every deck of card has 4 suits - Hearts, Diamonds, Clubs and Spades
suits = ['Clubs','Spades','Diamonds','Hearts']

#There are thirteen cards in every suite
card_designations = ['Ace','Two','Three','Four','Five','Six','Seven','Eight','Nine','Ten','Jack','Queen','King']

#Assigning the value to every card using a dictionary
card_values = {'Two':2, 'Three':3, 'Four':4, 'Five':5, 'Six':6, 'Seven':7, 'Eight':8, 'Nine':9, 'Ten':10, 'Jack':10, 'Queen':10, 'King':10, 'Ace':11}


playing = True #To be used later in a while loop

#Creating a card class
class Card:
    def __init__(self,suit,designation): #Every card is defined by a suit and rank/designation
        self.suit = suit
        self.designation = designation

    def __str__(self):
        return f"{self.designation} of {self.suit}" #Prints out designation of suit, for ex - two of hearts

#Creating a deck class for a deck of cards
class Deck:
    def __init__(self):
        self.deck = [] #Start with an empty list

        #looping through all the cards
        for suit in suits:
            for desig in card_designations:
                self.deck.append(Card(suit,desig)) #Creates a deck of 52 cards

    def shuffle(self):
        #Shuffles the deck of cards
        return random.shuffle(self.deck)

    def deal_card(self):
        #removes one card from the deck
        return self.deck.pop()

#Creating a class for the player hand
class Hand:
    def __init__(self):
        self.cards = [] #player starts out with zero cards
        self.value = 0 #Starts with zero
        self.aces = 0 #keeps track of the number of aces

    #Adds a card to the player hand
    def add_card(self,card):
        self.cards.append(card)
        self.value += card_values[card.designation] #Increments the value with each added card

        if card.designation == 'Ace': #keeping track of the number of aces
            self.aces += 1

    #An ace can have a value of 1 or 11 depending on the player
    #It is adjusted over here
    def ace_adjustment(self):
        while self.value > 21 and self.aces: #Checks if there are any ace cards
            self.value -= 10
            self.aces -= 1

#Creating a class to keep track of the player's chips
class Chips:
    def __init__(self,total = 100): #Default is hundred however any value can be passed in 
        self.total = total
        self.bet = 0

    #If the player wins
    def win_bet(self):
        self.total += self.bet

    #If the dealer wins/player loses
    def lose_bet(self):
        self.total -= self.bet

##Creating supporting functions

#Asks the player for a bet
def ask_for_bet(chips):

    #keeps asking until the correct value is entered
    while True:

        #Handling errors
        try:
            chips.bet = int(input("\nHow many chips do you want to bet? "))
        except:
            print("Please enter a number only!")
            continue

        #Makes sure that a player cannot bet more than they have
        if chips.bet > chips.total:
            print("Insufficient chips")
            print("Your current chips are:",chips.total)
        else:
            break

#takes in the deck and the player's hand as input and adds a card from the deck to the player's hand
def hit(deck,hand):
    hand.add_card(deck.deal_card())
    hand.ace_adjustment() #adjusts for an ace

#Asks the player if they wanna hit or stand
#Takes in the deck and player's hand as input
def hit_or_stand(deck,hand):

    #Calling the global varaible in order to change its value
    global playing 

    #Keeps asking until correct value is entered
    while True:

        #assign the player's choice to a varaible
        choice = input("\nDo you want to hit or stand? Enter h or s: ")

        if choice[0].lower() == 'h': #In case the player enters anything starting with 'h'
            hit(deck,hand)
        elif choice[0].lower() == 's': #In case the player enters anything starting with 's'
            print("\nPlayer decided to stand. Dealer's turn")
            playing = False #Assigning a new value to the global variable
        else:
            print("Please enter either h or s only!")
        break

#Shows one card of the dealer and keeps the other one hidden. Shows both the player's cards.
def show_partial(player_hand,dealer_hand):
    print("\nDealer's Hand:")
    print("<Hidden Card>")
    print(dealer_hand.cards[1]) #Shows the second card of the dealer's hand

    #displays all the cards in the player's hand
    print("\nPlayer's Hand: ",*player_hand.cards,sep='\n') 

#Shows both the player's and the dealer's cards and displays the value of each
def show_whole(player_hand,dealer_hand):

    #displays all of the dealer's cards
    print("\nDealer's Hand: ",*dealer_hand.cards,sep='\n')
    print("\nThe value of the dealer's hand is: ",dealer_hand.value)

    #displays all of the player's cards
    print("\nPlayer's Hand: ",*player_hand.cards,sep='\n')
    print("\nThe value of the player's hand is: ",player_hand.value)


##Functions for diferent win scenarios

#If the player's hand is greater than the dealer's hand
def player_wins(chips):
    print("\nPlayer Wins!")
    chips.win_bet() #referencing the Chips class

#if the player crosses a value if 21
def player_busts(chips):
    print("\nPlayer Busts! Dealer wins")
    chips.lose_bet()

#If the dealer's hand is greater than the player's hand
def dealer_wins(chips):
    print("\nDealer Wins!")
    chips.lose_bet()

#if the dealer's hand exceeds 21
def dealer_busts(chips):
    print("\nDealer Busts! Player Wins!")
    chips.win_bet()

#if both the dealer's hand and the player's hand have the same value
def push():
    print("\nTie Game! It's a Push")

##Main game function
def play():
    global playing

    #keeps playing until the players stops
    while True:

        #opening message
        print("\n**************************************** WELCOME TO BLACKJACK ****************************************")

        #setting up the deck
        card_deck = Deck()

        #shuffling the deck
        card_deck.shuffle()

        #setting up the player's hand and dealing two cards to the player
        player_hand = Hand()
        player_hand.add_card(card_deck.deal_card()) #referencing the deck class
        player_hand.add_card(card_deck.deal_card())

        #setting up the dealer's hand and dealing two cards to the dealer
        dealer_hand = Hand()
        dealer_hand.add_card(card_deck.deal_card())
        dealer_hand.add_card(card_deck.deal_card())

        #setting up the player's chips
        player_chips = Chips()

        #asking the player for a bet
        ask_for_bet(player_chips)

        #display the cards while hiding one of the dealer's cards
        show_partial(player_hand,dealer_hand)

        #playing until the player busts
        while playing:

            #asking the player if they wanna hit or stand
            hit_or_stand(card_deck,player_hand)

            #display the cards partially
            show_partial(player_hand,dealer_hand)

            #Checking to see if the player busted
            if player_hand.value > 21:
                player_busts(player_chips)

                #displaying all the cards
                show_whole(player_hand,dealer_hand)
                break
        
        #continuing the game
        if player_hand.value <= 21:

            #dealer hits until their hand is 17 or less but not greater    
            while dealer_hand.value <=17:

                #perform the hit function by taking in the deck and dealer's card as parameters
                hit(card_deck,dealer_hand)

                #display all of the cards
                show_whole(player_hand,dealer_hand)

            #checking if someone has won

            #checking if the dealer busted
            if dealer_hand.value > 21:
                dealer_busts(player_chips)

            #checking if the dealer won
            elif dealer_hand.value > player_hand.value:
                dealer_wins(player_chips)

            #checking if the player won
            elif dealer_hand.value < player_hand.value:
                player_wins(player_chips)

            #checking if there's a tie/push
            else:
                push()

        #displaying the players earnings
        print("\nYour total number of chips is:",player_chips.total)

        #asking the player if they wanna play again
        ask = input("\nDo you want to play again? Enter yes or no: ")
        if ask[0].lower() == 'y':
            playing = True
            continue #continues on with the game
        else:
            print("Thank you for playing!") #game over
            break

play()

#Programmed by Rik Ganguli Biswas
#referenced Complete Python Bootcamp by Jose Portilla