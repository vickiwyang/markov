# markov

**MarkovModel.java** creates a [Markov model](https://en.wikipedia.org/wiki/Markov_chain) of order *k* based on a given input text. For each group of consecutive *k* characters in the input text (called a *k*-gram), we generate the probability that any individual character will appear after that *k*-gram.

**TextGenerator.java** uses `MarkovModel` to repeatedly generate probabilistic next characters for a total length *T*. For example, using the input file `beatles.txt` (not included; any text file may be substituted), which contains lyrics from popular Beatles songs,

```
% more beatles.txt
Words are flowing out like endless rain into a paper cup, They slither while they pass, they slip
away across the universe. Pools of sorrow waves of joy are drifting through my opened mind,
Possessing and caressing me. Jai guru deva om Nothing's gonna change my world Nothing's
gonna change my world. Nothing's gonna change my world Nothing's gonna change my world.
Images of broken light which dance before me like a million eyes, They call me on and on across
the universe, Thoughts meander like a restless wind Inside a letter box they Stumble blindly as
they make their way Across the universe Jai guru deva om Nothing's gonna change my world
```

running `TextGenerator 5 200 < beatles.txt` yields a model of order *5* and generates 200 characters in the linguistic style of the Beatles. It might look like this:

```
Words are flowing of Marigold way. I've been us instead. Please don't you want you don't do you, yeh, All my love. Oh dearest one, if you I adore. When your birthday to
you. (sha la la la la la la la 
```

Or this:

```
Words are drifting and seen searchin' for this money? Sonny, if you walk away. One, two, three, girls read and I dream will
see her walk about you walk around, yeh, It took me on
out. Daylight now ove
```

--

*This assignment was completed as part of COS 126 at Princeton University in Spring 2020.*
