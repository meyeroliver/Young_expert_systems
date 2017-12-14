# Young_expert_systems

For expert systems I worked in a team. we needed to implement a backward-chaining inference engine.
Rules, facts and queries are given in text file.A fact can be any uppercasealphabetical character.
For each of these queries, the program must, given the facts and rules given, tell if the query is true,
false, or undetermined.
By default, all facts are false, and can only be made true by the initial facts statement,
or by application of a rule. A fact can only be undetermined if the ruleset is ambiguous,
for example if I say "A is true, also if A then B or C", then B and C are undetermined.
If there is an error in the input, for example a contradiction in the facts, or a syntax
error, the program must inform the user of the problem.

• "AND" conditions. For example, "If A and B and [...] then X"
• "OR" conditions. For example, "If C or D then Z"
• "XOR" conditions. For example, "If A xor E then V". Remember that this
  means "exclusive OR". It is only true if one and only one of the operands is true.
• Negation. For example, "If A and not B then Y"
• Multiple rules can have the same fact as a conclusion
 "AND" in conclusions. For example, "If A then B and C"
• Parentheses in expressions. Interpreted in much the same way as an arithmetic
  expression.
  
How to expert system

1) cd into expert_system, then cd into src
2) To compile, enter the following command into the command line "javac -g *.java"
3) To execute the program, enter the following into the command line "java main"
4) Next user will be prompted to enter in a textfile. Select any of the given textfiles or feel free to create your own

Disclaimer
Initially the program will not run becuase of the files directory. Please enter "pwd" into your command line, copy it.
Now once you have copied it. Open up the ReadFile.java file and on line 14 replace that given path with the one you just copied.
Do not forget to put "/" after src.
