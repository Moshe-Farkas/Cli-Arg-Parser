# Usage:
Create an instance of ArgsParser and proivde it with the expceted args and their types format string.\
Format:  "arg1-name: data-type, arg2-name: data-type".\
Query values with get*data-type*Arg. returns null if not provided with value (false for bool).
# Extension: 
 To add a data type simply implment the DataTpe interface and add a case in 'ParseFormatArg' method.
 
 
 
