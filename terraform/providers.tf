terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0.0"
    }
  }

  backend "s3" {
    bucket = "fiap-3soat-g15-tech-challenge-state"
    key    = "live/terraform.tfstate"
    region = "sa-east-1"
  }
}

provider "aws" {
  region = var.region
}

#provider "kubernetes" {
#  host                   = data.aws_eks_cluster.default.endpoint
#  cluster_ca_certificate = base64decode(data.aws_eks_cluster.default.certificate_authority[0].data)
#
#  exec {
#    api_version = "client.authentication.k8s.io/v1beta1"
#    args        = ["eks", "get-token", "--cluster-name", data.aws_eks_cluster.default.id]
#    command     = "aws"
#  }
#}
