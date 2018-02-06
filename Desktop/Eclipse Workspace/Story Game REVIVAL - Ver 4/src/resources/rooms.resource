% Template:
% # <tag>
% [@story] <room story>
% [@desc] <room description>
% [@enimies] <enemy list number>
% [@chance] <spawn chance>
% [@east] <tag for east room>
% [@north] <tag for north room>
% [@west] <tag for west room>
% [@south] <tag for south room>
% & X
% [@desc] <examinable description>
% [@nickname] <possible name for this examinable>
% [... more nicknames ...]
% [... more examinables ...]
% & I
% [@item_id] <item id for the item in the room>
% [@amt] <item amount>
% [@nickname] <possible name for this item in this room>
% [... more nicknames ...]
% [... more items ...]

# Starting Room
!story You've awoken in a small strange room. The walls look slimy, but strudy. How did you get here? Where is here?
The room is fairly empty. However, there are some nails in the corner. They're lying on what looks like a bed.
0
50
& E
It's pretty gross, but it would probably work for a night's rest.
bed
& I
nail
1
nails

# END
% Don't write below this.