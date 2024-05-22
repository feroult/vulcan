mvn clean package dependency:tree
for foo in `cat ~/tmp/e/plugins.txt | grep ":system" | cut -d ":" -f 2`; do echo "<plugin id=\"$foo\" />"; done
