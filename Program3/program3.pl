% Major, Car, Team, Music, Drink
rooms(Hall) :-
    Hall = [_,_,room(cs,_,_,_,_),_,_], %1
    member(room(hist,_,_,jazz,_), Hall), %2
    member(room(_,toyota,yankees,_,_), Hall), %3
    member(room(acct,_,_,_,coke), Hall), %4
    member(room(engr,_,_,_,coffee), Hall), %5
    adjacent(room(cs,_,_,_,_), room(hist,_,_,_,_), Hall), %6
    Hall = [_,_,_,_,room(_,_,_,classical,_)], %7
    member(room(_,tesla,_,_,tea), Hall), %8
    adjacent(room(_,_,_,classical,_), room(_,_,_,jazz,_), Hall), %9
    member(room(eng,_,_,_,_), Hall), %10
    Hall \= [room(eng,_,_,_,_),_,_,_,_],
    Hall \= [_,room(eng,_,_,_,_),_,_,_],
    member(room(_,tesla,royals,_,_), Hall), %11
    member(room(_,_,cubs,jazz,_), Hall), %12
    member(room(engr,_,chiefs,_,_), Hall), %13
    Hall = [room(_,_,broncos,_,_),_,_,_,_], %14
    member(room(_,nissan,_,_,coke), Hall), %15
    adjacent(room(_,_,_,country,_), room(_,_,_,techno,_), Hall), %16
    Hall = [room(acct,_,_,_,_),_,_,_,_], %17
    adjacent(room(_,_,chiefs,_,_), room(_,_,royals,_,_), Hall), %18
    member(room(acct,_,_,rock,_), Hall), %19
    member(room(_,_,yankees,_,milk), Hall), %20
    member(room(_,chevy,_,country,_), Hall), %21
    member(room(_,ford,_,jazz,_), Hall). %22

adjacent(R1,R2,H) :-
    append(_, [R1,R2|_], H);
    append(_, [R2,R1|_], H).
