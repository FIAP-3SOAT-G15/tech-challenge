data "aws_caller_identity" "current" {}

module "ecr" {
  source  = "terraform-aws-modules/ecr/aws"
  version = "1.6.0"

  repository_read_write_access_arns = [data.aws_caller_identity.current.arn]

  repository_name = "tech-challenge"

  repository_force_delete = true

  repository_image_tag_mutability = "MUTABLE"
  repository_image_scan_on_push   = false
}
