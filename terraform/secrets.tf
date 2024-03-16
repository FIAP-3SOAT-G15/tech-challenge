module "secrets_manager" {
  source  = "terraform-aws-modules/secrets-manager/aws"
  version = "1.1.2"

  name = "live/selfordermanagement/mercadopago"

  recovery_window_in_days = 0

  # For security reasons, insert values manually after apply
  secret_string = jsonencode({
    token            = null
    user-id          = null
    pos-id           = null
    webhook-base-url = null
  })
}

resource "aws_iam_policy" "mercado_pago_secrets_read_only_policy" {
  name = "TechChallengeMercadoPagoReadOnlyPolicy"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "secretsmanager:DescribeSecret",
          "secretsmanager:GetSecretValue"
        ],
        Resource = module.secrets_manager.secret_arn
      }
    ]
  })
}
