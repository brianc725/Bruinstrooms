restroom_code = input("Enter Restroom Code (EG: MS4317M)\n")
gender = input("Enter Restroom Gender all lowercase\n")
urinals = input("Enter number of urinals\n")
urinalDividers = input("Enter 'yes/no' for urinal dividers\n")
stalls = input ("Enter number of stalls\n")
hasHandicap = input("Enter 'yes/no' for has handicap stalls\n")
sinks = input("Enter number of sinks\n")
mirrors = input("Enter number of mirrors\n")

print('Restroom {} = new Restroom("{}", "{}", "{}", "{}", "{}", "{}", "{}", "{}");'.format(restroom_code, restroom_code, gender, urinals, urinalDividers, stalls, hasHandicap, sinks, mirrors))
print('restroomsRef.child("restrooms").child({}.getName()).setValue({});'.format(restroom_code, restroom_code))