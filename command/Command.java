package command;

import java.util.List;

public record Command(

        Commands type,

        List<String> arguments

) {}


// MSET
// name
// Pratik
// age
// 21
// city
// Rajkot
// Changed to List<> because a fixed key and value won't scale 



// Now its like tthis 
// SET → ["name", "Pratik"]
// GET → ["name"]
// DEL → ["name"]
// LPUSH → ["numbers", "1", "2", "3", "4", "5"]