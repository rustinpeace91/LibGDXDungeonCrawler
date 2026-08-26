./gradlew teavm:buildRelease

# very crude and should be turned into env variables or something, but will work for now
sudo rm -r /home/bazzite/code/java/dragon-quarrel-deployment/*
cp -r /home/bazzite/code/java/LibGDXDungeonCrawler/teavm/build/dist/webapp/* /home/bazzite/code/java/dragon-quarrel-deployment
