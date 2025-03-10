## K8S 환경 구축하기

1. minikube 소개
minikube는 가벼은 K8S 구현체 입니다. 로컬 머신에 클러스터를 구성하기 위해 vm을 사용합니다.
2.  vm 도구 설치
brew install docker
3. kubectl & minikube 설치
brew install kubectl
brew install minikube
4. 설치 완료 테스트
docker 데스크탑 실행
minikube start
5. kubectl get all
6. https://hub.docker.com/ 가입 후 image push
docker build -t {hub id}/{repo name}:0.0 .
docker push {hub id}/{repo name}:0.0