% Template:
%
% # <tag>
% [* <openning stage direction>]
% <dialog>
% [* <closing stage direction>]
% 
% [!start <new tag to start the conversation at>]
% 
% [<response option>]
% [<tag for response to response option>]
% [more options...]

# START
* You tap the mysterious man on the shoulder and he looks at you, surprised.
Oh, hello there. I thought you'd never wake up. 

!start Second Start

Where are we?
2
Who are you?
4
Sorry, didn't mean to disturb you.
1


# 1
Well, goodbey.
* The man looks back at the wall, again oblivious to your precesnse.

Bye
END

# 2
Boy, I've been trying to figure that out for years. All I know is that there's some kind of maze out there.
* He gestures to the door to the east.

What's out there?
3


# 3
From what I've seen, not much. I've been searching for away out for months, but the only thing I've found is this key.
* The man takes out a shiny gold key from his tattered coat pocket.

What does it open?
4
Can I have it? I think I'm better than you at puzzles, old man.
5


# 5
Wow, well screw you, too. How's about you go back to your corner and I'll go back to mine and we'll both die in piece.

!start Angered Start
!leave


# 4
That I don't know. I've looked up and down the walls of this cavern, but I haven't been able to find any key hole. Hey, now that you're awake, you think you want to give it a shot? A pair of fresh eyes can't hurt.

Sure, it's not like I've got anything better to do!
6
Nah.
7


# 6
Ok, well I guess you can take you key. Here you go.
* The man gives you the key. (It was added to your inventory. You can open your inventory by typing "invt" once this long, drawn out conversation ends.)

!start Found Anything Yet Start
!execute /give key_one
!desc The cool guy who gave you the key!

Cool, thanks!
@goto Found Anything Yet Start


# Found Anything Yet Start
* The man looks up at you, hopefully.
Found anything yet?

Yep.
END
Nope.
END

# 7 
Ok. So, how's life...?

!start 7

Ugh, alright, fine! I'll go on your quest.
6
This is boring. Bye.
END


# Angered Start
What do you want?

I'm sorry about before.
8
I just wanted to say, 'Screw you!' again.
5


# 8
Well, I guess we should try to not hate each other. I mean after all, we are stuck here together.

!start Second Start

Yeah, let's work together!
Second Start


# Second Start
* The man looks up at you again.
Yes?


# END


# START
* You tap Mac on his shoulder.
Oh hey Dave! What's up?

Um, you know... just stuck in this dunegon... like you.
1
Not much. You?
2

# 1
Oh yeah, pretty crazy right?

Uh, yeah, we're trapped in this creppy place!
1-1
Nah. I'm at places liek this almost every night.
1-2

# 1-2
Oh man, good ole Dave, the party animal! 
