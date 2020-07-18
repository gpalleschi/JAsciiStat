# JAsciiStat
A java line command tool to calculate statistic on ascii files new line terminated, using regular expression rules.
---
## Prerequisites
>   java 1.7 or upper
---
## Running
>   java -jar JAsciiStat.jar "File Ascii" [-p"separator char File Statistic"] -s"File Name Statistic"  
[...] are optional parameters  
"File Name Statistic"       :   Is a csv file with default separator "|"  

Each record in Statistic File has a csv format like this "Type"|"Regular Expression Condition"|"Regular Expression Extraction"|"Label"  
  
- Type                            : Z for Clean Statistic Key formed by Dimension and reinitialize conditions (format : 'Z|"Regular Expression Condition"')  
                                    C for condition (format : 'C|"Regular Expression Condition"')  
                                    D for dimension (format : 'D|"Regular Expression Condition"|"Regular Expression Extraction"')  
                                    I for Counters (format : 'I|"Regular Expression Condition"|"Label to show in resume"')  
                                    M for metrics to sums (format : 'M|"Regular Expression Condition"|"Regular Expression Extraction"|"Label to show in resume"')  
                                    T for statistic title (format : 'T|"Label to show in resume"')  
  
- Regular Expression Condition  : Is an expression to check if record read from file matches with it (true or false) is used for type Z, C, D, I, M  
- Regular Expression Extraction : Is an exprssion to extract value from record read from file is used for type D and M  
- Label                         : Label used for type M,I and T, if isn't specified will be assigned default values.  
  
You can specify more than one **Statistic Files**.  
  
---
## Workflow
For every record reads from **File Ascii** and for every **Statistic File**  
  
1. Execute all **Cleaner** expressions  
2. If all **Cleaner** expressions are true  
    - Add Key  
    - Reinitialize Key  
    - Reinitialize Conditions  
3. Execute **Incremental** expressions  
4. Check all **Condition** expressions  
5. If all **Condition** expressions are true  
    - Execute **Dimension** expressions  
    - If length of key calculated is more than 0  
        * Execute **Metric** expressions  
---
## Example Regular Expression
>   An examples of regular expressions to use with this tool :

- (?<=\\[)([^\\]]+)(?=\\])    : Extract string value between square brackets [...].
- (?<=\\()([^)]+)(?=\\))      : Extract string value between round brackets (...).
- (?<=\\{)([^}]+)(?=\\})      : Extract string value between curly brackets {...}.
- (?<=.{4})(.{3})             : Extract string from byte 5 for 3 bytes.
- ^.*$                        : Condition Always True.
- ^.{3}abc.*$              : Condition to check if at byte 4 is present string abc.
---
## Built With

* [Eclipse](https://www.eclipse.org/)

---
## Author
[Giovanni Palleschi](https://github.com/gpalleschi "GitHub")