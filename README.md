## CS-461 Projects
**Introduction to Artificial Intelligence**

### Program 1 (8-Puzzle)
An 8-puzzle is a 3x3 grid of tiles, numbered 1-8, with the last square in the grid being empty.  A tile can be slid into the blank spot, thus changing the configuration of the puzzle.
![](https://github.com/evanwike/CS-461/blob/master/img/puzzle.png)

Given an arbitrary arrangement of tiles, can this goal state be reached? Not necessarily; half of all permutations are such that reaching the goal state is impossible.  (All states have either odd or even parity; no state with odd parity is reachable from any state with even parity, and vice-versa. The proof relies on a bit of number theory, not relevant here, and not essential for this program.)  Your task is to write a program that determines if the goal state can be reached, and if so, the series of moves (path) needed to reach it.

Your input is a text file with a series of puzzles. Each is a 3x3 grid of integers, with 0 indicating the blank square.  For each, output is either a statement that no path exists, or a listing of the tiles (by number) that must be moved, in order, to reach a solution.

Determining a path exists: You will be searching through possible puzzle states, tracking already-visited states so you don't return to a state you've already examined. This will eventually search all states reachable from your starting state. If none of them is the goal--it's not reachable! (This isn't as awful as it sounds; from any state, there are only about 180,000 reachable states, and moving from one state to another is a fast operation.  For larger problems--a 15-puzzle or 24-puzzle--you'd definitely want a more efficient determination.) Of course, if you want to read up on parity and check that directly, that's OK too.

Finding the path: Use the A* algorithm. You will need a data structure that stores puzzle states you have already checked (so you don't visit them again), and a 'pending' queue which can keep unvisited states ordered by estimated distance from the goal state. Use the Manhattan (city-block) distance from the goal as your heuristic. That is, for each tile, how far away is this tile from its goal position?
***
### Program 2 (University Class Schedule)
For this program you will use simulated annealing to solve a small example of a real-world problem.

**Consider the problem of producing a university class schedule:**
* Each course must be taught.
* It must have a room, and a time.
* Only one course can be taught in a room at a time.  
* The room must be able to hold the expected number of students.
* It must have an instructor.
* Each instructor can teach any of several courses, but only those courses, and there is an upper limit on how many courses one instructor can teach.
* A course can be taught by an adjunct or graduate student ('Staff'), but faculty are preferred for teaching.
* Finally, we may have additional preferences regarding scheduling; for example, if there are courses that are usually taken the same semester, we would prefer (but not require) that they be taught in adjacent time slots, and if they’re in adjacent time slots, that they be in rooms that are close together. If a course has multiple sections, those sections should be separated in time; it doesn't make sense to have a 1 PM section, then a 2 PM section of the same course.

You are given a list of 12 courses. (Some of these may be multiple sections of the same course, but that doesn’t affect our problem here.) You also have a list of several faculty members, and the courses each can teach. You also have a list of available rooms and time slots. Your task is to use simulated annealing to devise a suitable teaching schedule.

In a production system, we’d probably want the program to read the various options (courses, instructors, etc) from input files, but for this assignment you can use input files or put the data directly into your source code.

**Courses and expected enrollments are:** CS 101A (40), CS 101B (25), CS 201A (30), CS 201B (30), CS 191A (60), CS 191B (20), CS 291B (40), CS 291A (20), CS 303 (50), CS 341 (40), CS 449 (55), CS 461 (40).

**Instructors and what they can teach:**
* Hare: CS 101, CS 201, CS 291, CS 303, CS 449, CS 461
* Bingham: CS 101, CS 201, CS 191, CS 291, CS 449
* Kuhail: CS 303, CS 341
* Mitchell: CS 191, CS 291, CS 303, CS 341
* Rao: CS 291, CS 303, CS 341, CS 461
* Staff: any

**Time slots:** 10A, 11A, 12P, 1P, 2P, 3P, 4P (We’re assuming these are all MWF courses)

**Rooms and capacities:** Haag 301 (70), Haag 206 (30), Royall 204 (70), Katz 209 (50), Flarsheim 310 (80), Flarsheim 260 (25), Bloch 0009 (30)

**Fitness function:**
Assign instructors, times, rooms, and courses. For your initial schedule, this will be random. Assess the fitness function as follows:
* For each course that is taught by an instructor who can teach it, other than Staff: +3
* For each course taught by Staff: +1
* For each course that is the only course scheduled in that room at that time: +5
* For each course that is in a room large enough to accommodate it: +5
  * Room capacity is no more than twice the expected enrollment: +2
* For each course that does not have the same instructor teaching another course at the same time: +5
* For each schedule that has the same instructor teaching more than 4 courses: -5 per course over 4
* For each schedule that has Rao or Mitchell (graduate faculty) teaching more courses than Hare or Bingham (same number of courses is OK): -10
* CS 101 and CS 191 are usually taken the same semester; the same applies to CS 201 and CS 291. Therefore apply these rules to those pairs of courses:
  * Courses are scheduled for same time: -15
  * Courses are scheduled for adjacent times: +5
  * if these courses are scheduled for adjacent times, and
    * Are in the same building: +5 points
    * Are both on the quad (Haag, Royall, Flarsheim): no modification
    * 1 is in Katz and the other isn’t: -3
    * 1 is in Bloch and the other isn’t: -3
    * (Yes, if one’s in Katz and the other’s in Bloch, that’s -6)
  * CS101A and CS101B are scheduled 3 hours apart or more: +5
  * CS191A and CS191B are scheduled 3 hours apart or more: +5

**Annealing Schedule:** It's necessary to assign instructor, time, and room to each of 12 courses, so there are 36 items to be assigned. Lower your temperature parameter after 400 successful changes to the schedule, or 4000 attempts. Continue until you make a complete pass (4000 attempts) with no changes. Print your final schedule to an output file, along with its fitness score.

You may want to try running your program several times and see how much variation there is in the schedule or fitness score across multiple runs. Likewise, you may want to change your annealing schedule to reduce the temperature more slowly (T = 0.95T rather than T = 0.9T) and explore the relationship between running time and the fitness of the final schedule.

You may write your program in C, C++, C#, Java, or Python. (If you’ve got something else you want to use, talk to me, we’ll discuss it.) Submit your program source code, and a sample program run with the schedule it produces. Also write up a short report discussing your program, what data structures you used, or ideas for how this program could be extended or generalized.
***
### Program 3 (PROLOG)
For this program, you are to write a program in PROLOG to solve the following logic problem. Note that 'adjacent to' or 'neighbor' may be on the left or the right unless other constraints dictate otherwise. And of course, the neighbor relation is symmetric. If A is a neighbor of B, then B is a neighbor of A.

A college dorm hallway has 5 1-person rooms. Each resident is a different major, listens to a different style of music, drives a different make of car, has a different favorite sports team, and a different favorite beverage.

* The computer science student lives in the middle of the corridor
* The history major is a jazz fan.
* The Yankees fan drives a Toyota.
* The accounting major drinks Coke.
* The engineering major drinks coffee.
* The computer science student and history student are neighbors.
* The student at the far end of the hall likes classical music
* The tea drinker drives a Tesla.
* The classical music fan lives next to the jazz listener.
* The English major does not live in either of the first two rooms.
* The Royals fan drives a Tesla.
* The Cubs fan listens to jazz.
* The engineering major follows the Chiefs
* The first room is the home of the Broncos fan
* The Coke drinker drives a Nissan.
* The country music fan and the techno fan are neighbors.
* The accounting major lives in the first room.
* The fans of the 2 Kansas City teams (Chiefs and Royals) are neighbors
* The accounting major listens to rock music
* The Yankees fan drinks milk.
* The Chevy driver listens to country music.
* The jazz fan drives a Ford.

*What music does the computer-science student listen to and what does the English major drink?*

**Summary:**
* **Majors:** Accounting, Computer Science, Engineering, History, English
* **Cars:** Ford, Chevy, Nissan, Toyota, Tesla
* **Sports Teams:** Royals, Chiefs, Yankees, Broncos, Cubs
* **Music:** Classical, Country, Jazz, Rock, Techno
* **Drinks:** Coke, Coffee, Tea, Milk, Water

Write a PROLOG program that contains the above facts as a knowledge base, and functions to capture 'adjacent to,' 'all different,' etc. Your program should begin with the above facts and deduce the major, car, music, sports team, and beverage for each resident, printing the information at the end. Submit your program code and output from a sample execution.
***
### Program 4 (TensorFlow)
For this assignment, you'll be using some real-world data to predict product quality. The data will need some initial preparation before processing.

Your data file is based on reviews from The Ramen Rater, a site for serious ramen fans. The data consists of 2580 product reviews. For each, you have:
* Identifier (unique integer)
* Brand (355 values)
* Variety (2413 unique values)
* Style (8 values)
* Country of origin (38 values)
* Stars (numeric rating)
* Top Ten (text, missing on most entries, ignore)

You will need to do some initial data configuration and recoding. This can (and should) be done separately, before using TensorFlow to build your network. Specifically:
* Some companies have multiple products, others only appear once. Count the number of times each company name appears. Any company appearing only once should be replaced with "Other".  (With only 1 product, there's no way to generalize about the company.)
* The "variety" field has the richest information, but also needs some distillation before we can use it conveniently as input into a neural network. We'd like to identify specific features--flavors, descriptions, etc--that might tell us something. So we have to get it into that form. Again, this should be done separately:
  * Take the text from the Variety column, and break it into words.
  * Count the occurrences of each word and find the 100 most common words. Keep ONLY those words for classification. (Note that some reviews might have several of those popular words, others might not have any.)
  * Code for the presence or absence of each of the most common 100 words. (Note that this is not one-hot coding; if 5 of the words are present, then all 5 should be accounted for in input, not just one.)

Once the data is ready for input, build a neural network in TF to predict the overall rating. Your rating should be by categories, 0-5 stars. Use 1-hot coding for the output (maximum value is taken as the classification).

Write up a short report discussing:
* The configuration of your network, along with a brief overview of your TF code. It is not necessary to discuss the (python, perl, whatever) code you used to clean up the data.
* Your cross-validation strategy
* Summarize your results
* Any further questions you'd like to discuss, ideas for extending this further, etc.
***
