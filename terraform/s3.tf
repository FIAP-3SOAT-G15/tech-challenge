resource "aws_s3_bucket" "website_bucket" {
  bucket = var.website_bucket_name
  tags   = var.tags
}

resource "aws_s3_bucket_website_configuration" "website_configuration" {
  bucket = aws_s3_bucket.website_bucket.id

  index_document {
    suffix = "index.html"
  }

  error_document {
    key = "404.html"
  }
}

resource "aws_s3_bucket_public_access_block" "website_bucket_public_access_block" {
  bucket = aws_s3_bucket.website_bucket.id

  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}

resource "aws_s3_bucket_policy" "website_bucket_policy" {
  bucket = aws_s3_bucket.website_bucket.id

  policy = data.aws_iam_policy_document.website_bucket_policy_document.json
}

data "aws_iam_policy_document" "website_bucket_policy_document" {
  statement {
    sid = "PublicReadGetObject"

    effect = "Allow"

    principals {
      type        = "*"
      identifiers = ["*"]
    }

    actions = [
      "s3:GetObject",
    ]

    resources = [
      "arn:aws:s3:::${var.website_bucket_name}/*"
    ]
  }
}
