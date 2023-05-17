This project aims to evaluate a template agains all possible combinations for a given list of inputs.

For example:
Template -> `{'user': '$user', 'dob': '$dob'}`
Input -> 
```{
user: ["Dhruva", "Chandra"],
dob: ["1", "2", "3"]
}
```

generates output of
```
{ 'user': 'Dhruva', 'dob': '1' }
{ 'user': 'Chandra', 'dob': '1' }
{ 'user': 'Dhruva', 'dob': '2' }
{ 'user': 'Chandra', 'dob': '2' }
{ 'user': 'Dhruva', 'dob': '3' }
{ 'user': 'Chandra', 'dob': '3' }
{ 'user': 'Dhruva', 'dob': '4' }
{ 'user': 'Chandra', 'dob': '4' }
```
